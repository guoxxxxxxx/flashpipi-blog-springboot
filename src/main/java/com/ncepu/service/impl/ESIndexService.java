package com.ncepu.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Time: 2025/9/1 19:42
 * @Author: guox
 * @File: ESIndexService
 * @Description:
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ESIndexService {

    private final ElasticsearchClient client;

    private static final String INDEX_NAME = "blog_index";

    /**
     * 创建博客索引（自动检查是否存在）
     */
    public void createBlogIndex() throws IOException {
        // 1️⃣ 检查索引是否存在
        BooleanResponse existsResp = client.indices().exists(ExistsRequest.of(e -> e.index(INDEX_NAME)));
        if (existsResp.value()) {
            log.info("索引已存在: " + INDEX_NAME);
            return;
        }

        // 2️⃣ 创建索引并映射字段
        CreateIndexResponse response = client.indices().create(
                CreateIndexRequest.of(c -> c
                        .index(INDEX_NAME)
                        .mappings(m -> m
                                .properties("id", p -> p.integer(i -> i))
                                .properties("title", p -> p.text(t -> t.analyzer("ik_max_word")))
                                .properties("description", p -> p.text(t -> t.analyzer("ik_smart")))
                                .properties("content", p -> p.text(t -> t.analyzer("ik_max_word")))
                                .properties("imagePath", p -> p.keyword(k -> k))
                                .properties("publishTime", p -> p.date(d -> d.format("yyyy-MM-dd")))
                                .properties("updateTime", p -> p.date(d -> d.format("yyyy-MM-dd")))
                                .properties("category", p -> p.keyword(k -> k))
                                .properties("collection", p -> p.keyword(k -> k))
                                .properties("viewsCount", p -> p.integer(i -> i))
                                .properties("sortId", p -> p.integer(i -> i))
                                .properties("deleteBit", p -> p.boolean_(b -> b))
                        )
                )
        );

        // 3️⃣ 打印结果
        if (response.acknowledged()) {
            log.info("索引创建成功: " + INDEX_NAME);
        } else {
            log.info("索引创建失败: " + INDEX_NAME);
        }
    }
}
