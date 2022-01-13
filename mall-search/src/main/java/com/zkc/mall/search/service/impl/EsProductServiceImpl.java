package com.zkc.mall.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zkc.mall.search.dao.EsProductDao;
import com.zkc.mall.search.domain.EsProduct;
import com.zkc.mall.search.domain.EsProductRelatedInfo;
import com.zkc.mall.search.respository.EsProductRepository;
import com.zkc.mall.search.service.EsProductService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EsProductServiceImpl implements EsProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);
	
	@Resource
	private EsProductDao productDao;
	@Resource
	private EsProductRepository esProductRepository;
	
	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	
	@Override
	public int importAll() {
		
		List<EsProduct> esProductList = productDao.getAllEsProductList(null);
		Iterable<EsProduct> esProductIterable = esProductRepository.saveAll(esProductList);
		Iterator<EsProduct> esProductIterator = esProductIterable.iterator();
		int result = 0;
		while (esProductIterator.hasNext()) {
			result++;
			esProductIterator.next();
		}
		return result;
	}
	
	@Override
	public void delete(Long id) {
		esProductRepository.deleteById(id);
	}
	
	@Override
	public void delete(List<Long> ids) {
		esProductRepository.deleteAllById(ids);
	}
	
	@Override
	public EsProduct create(Long id) {
		EsProduct result = null;
		List<EsProduct> esProductList = productDao.getAllEsProductList(id);
		if (CollUtil.isNotEmpty(esProductList)) {
			EsProduct product = esProductList.get(0);
			result = esProductRepository.save(product);
		}
		return result;
	}
	
	@Override
	public List<EsProduct> search(String keyword, Integer pageSize, Integer pageNum) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
	}
	
	@Override
	public List<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer sort, Integer pageSize, Integer pageNum) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		//分页
		nativeSearchQueryBuilder.withPageable(pageable);
		//过滤
		if (brandId != null || productCategoryId != null) {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			if (brandId != null) {
				boolQueryBuilder.must(QueryBuilders.termQuery("brandId", brandId));
			}
			if (productCategoryId != null) {
				boolQueryBuilder.must(QueryBuilders.termQuery("productCategoryId", productCategoryId));
			}
			nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
		}
		//
		if (StrUtil.isEmpty(keyword)) {
			nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
		} else {
			
			List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
					ScoreFunctionBuilders.weightFactorFunction(10)));
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword),
					ScoreFunctionBuilders.weightFactorFunction(5)));
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword),
					ScoreFunctionBuilders.weightFactorFunction(2)));
			FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders =
					new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
			filterFunctionBuilders.toArray(builders);
			FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
					.scoreMode(FunctionScoreQuery.ScoreMode.SUM).setMinScore(2);
			
			nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
		}
		//排序
		switch (sort) {
			case 1:
				//按新品从新到旧
				nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("id").order(SortOrder.DESC));
				break;
			case 2:
				//按销量从高到低
				nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
				break;
			case 3:
				//按价格从低到高
				nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.ASC));
				break;
			case 4:
				//按价格从高到低
				nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.DESC));
				break;
		}
		//按相关度
		nativeSearchQueryBuilder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
		
		NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
		LOGGER.info("DSL:{}", nativeSearchQuery.getQuery());
		SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, EsProduct.class);
		List<EsProduct> searchProducts = new ArrayList<>();
		if (searchHits.getTotalHits() <= 0) {
			return searchProducts;
		} else {
			searchProducts = searchHits.stream().map(h -> h.getContent()).collect(Collectors.toList());
		}
		
		return searchProducts;
	}
	
	@Override
	public List<EsProduct> recommend(Long id, Integer pageSize, Integer pageNum) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		
		List<EsProduct> searchProducts = new ArrayList<>();
		List<EsProduct> esProductList = productDao.getAllEsProductList(id);
		if (CollUtil.isNotEmpty(esProductList)) {
			EsProduct esProduct = esProductList.get(0);
			String keyword = esProduct.getName();
			Long brandId = esProduct.getBrandId();
			Long productCategoryId = esProduct.getProductCategoryId();
			//根据商品标题、品牌、分类进行搜索
			List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword),
					ScoreFunctionBuilders.weightFactorFunction(8)));
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword),
					ScoreFunctionBuilders.weightFactorFunction(2)));
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", keyword),
					ScoreFunctionBuilders.weightFactorFunction(2)));
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("brandId", brandId),
					ScoreFunctionBuilders.weightFactorFunction(2)));
			filterFunctionBuilders.add(new FunctionScoreQueryBuilder.
					FilterFunctionBuilder(QueryBuilders.matchQuery("productCategoryId", productCategoryId),
					ScoreFunctionBuilders.weightFactorFunction(3)));
			FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders =
					new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
			filterFunctionBuilders.toArray(builders);
			FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
					.scoreMode(FunctionScoreQuery.ScoreMode.SUM).setMinScore(2);
			//过滤掉相同的商品
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", id));
			
			
			NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
			nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
			nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
			nativeSearchQueryBuilder.withPageable(pageable);
			
			NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
			LOGGER.info("DSL:{}", nativeSearchQuery.getQuery());
			SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, EsProduct.class);
			if (searchHits.getTotalHits() <= 0) {
				return searchProducts;
			} else {
				searchProducts = searchHits.stream().map(h -> h.getContent()).collect(Collectors.toList());
			}
		}
		return searchProducts;
	}
	
	@Override
	public EsProductRelatedInfo searchRelatedInfo(String keyword) {
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		if (StrUtil.isEmpty(keyword)) {
			nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
		} else {
			nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(keyword, "name", "subTitle", "keywords"));
		}
		//聚合品牌名称
		nativeSearchQueryBuilder.withAggregations(AggregationBuilders.terms("brandNames").field("brandName"));
		//聚合分类名称
		nativeSearchQueryBuilder.withAggregations(AggregationBuilders.terms("productCategoryNames").field("productCategoryName"));
		//聚合商品属性
		NestedAggregationBuilder aggregationBuilder = AggregationBuilders.nested("allAttrValues", "attrValueList")
				.subAggregation(AggregationBuilders.filter("productAttrs", QueryBuilders.termQuery("attrValueList.type", 1)))
				.subAggregation(AggregationBuilders.terms("attrIds").field("attrValueList.productAttributeId")
						.subAggregation(AggregationBuilders.terms("attrValues").field("attrValueList.value"))
						.subAggregation(AggregationBuilders.terms("attrNames").field("attrValueList.name")));
		
		nativeSearchQueryBuilder.withAggregations(aggregationBuilder);
		NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
		SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, EsProduct.class);
		
		return convertProductRelatedInfo(searchHits);
	}
	
	private EsProductRelatedInfo convertProductRelatedInfo(SearchHits<EsProduct> searchHits) {
		EsProductRelatedInfo esProductRelatedInfo = new EsProductRelatedInfo();
		Map<String, Aggregation> aggregationMap = ((Aggregations) searchHits.getAggregations().aggregations()).getAsMap();
		//设置品牌
		Aggregation brandNames = aggregationMap.get("brandNames");
		List<String> brandNameList = new ArrayList<>();
		for (Terms.Bucket bucket : ((Terms) brandNames).getBuckets()) {
			brandNameList.add(bucket.getKeyAsString());
		}
		esProductRelatedInfo.setBrandNames(brandNameList);
		
		//设置分类
		Aggregation productCategoryNames = aggregationMap.get("productCategoryNames");
		List<String> productCategoryNameList = new ArrayList<>();
		for (Terms.Bucket bucket : ((Terms) productCategoryNames).getBuckets()) {
			productCategoryNameList.add(bucket.getKeyAsString());
		}
		esProductRelatedInfo.setProductCategoryNames(productCategoryNameList);
		
		//设置属性
		List<EsProductRelatedInfo.ProductAttr> attrList = new ArrayList<>();
		
		Aggregation productAttrs = aggregationMap.get("allAttrValues");
		List<? extends Terms.Bucket> attrIdBuckets = ((ParsedStringTerms) ((ParsedFilter) ((ParsedNested) productAttrs)
				.getAggregations().get("productAttrs"))
				.getAggregations().get("attrIds")).getBuckets();
		for (Terms.Bucket attrIdBucket : attrIdBuckets) {
			
			EsProductRelatedInfo.ProductAttr productAttr = new EsProductRelatedInfo.ProductAttr();
			
			productAttr.setAttrId((Long) attrIdBucket.getKey());
			
			List<String> attrValueList = new ArrayList<>();
			List<? extends Terms.Bucket> attrValues = ((ParsedStringTerms) attrIdBucket.getAggregations().get("attrValues")).getBuckets();
			List<? extends Terms.Bucket> attrNames = ((ParsedStringTerms) attrIdBucket.getAggregations().get("attrNames")).getBuckets();
			for (Terms.Bucket attrValue : attrValues) {
				attrValueList.add(attrValue.getKeyAsString());
			}
			productAttr.setAttrValues(attrValueList);
			
			if (CollUtil.isNotEmpty(attrNames)) {
				productAttr.setAttrName(attrNames.get(0).getKeyAsString());
			}
			
			attrList.add(productAttr);
		}
		
		esProductRelatedInfo.setProductAttrs(attrList);
		
		return esProductRelatedInfo;
	}
	
}
	
