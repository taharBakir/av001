package com.av001.web.rest;

import com.av001.Application;
import com.av001.domain.Lineitem;
import com.av001.repository.LineItemRepository;
import com.av001.repository.search.LineItemSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LineItemResource REST controller.
 *
 * @see LineItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LineItemResourceTest {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBB";

    @Inject
    private LineItemRepository lineItemRepository;

    @Inject
    private LineItemSearchRepository lineItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLineItemMockMvc;

    private Lineitem lineItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LineItemResource lineItemResource = new LineItemResource();
        ReflectionTestUtils.setField(lineItemResource, "lineItemRepository", lineItemRepository);
        ReflectionTestUtils.setField(lineItemResource, "lineItemSearchRepository", lineItemSearchRepository);
        this.restLineItemMockMvc = MockMvcBuilders.standaloneSetup(lineItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lineItem = new Lineitem();
        lineItem.setProductName(DEFAULT_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void createLineItem() throws Exception {
        int databaseSizeBeforeCreate = lineItemRepository.findAll().size();

        // Create the LineItem

        restLineItemMockMvc.perform(post("/api/lineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lineItem)))
                .andExpect(status().isCreated());

        // Validate the LineItem in the database
        List<Lineitem> lineItems = lineItemRepository.findAll();
        assertThat(lineItems).hasSize(databaseSizeBeforeCreate + 1);
        Lineitem testLineItem = lineItems.get(lineItems.size() - 1);
        assertThat(testLineItem.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllLineItems() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        // Get all the lineItems
        restLineItemMockMvc.perform(get("/api/lineItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lineItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

        // Get the lineItem
        restLineItemMockMvc.perform(get("/api/lineItems/{id}", lineItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lineItem.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLineItem() throws Exception {
        // Get the lineItem
        restLineItemMockMvc.perform(get("/api/lineItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

		int databaseSizeBeforeUpdate = lineItemRepository.findAll().size();

        // Update the lineItem
        lineItem.setProductName(UPDATED_PRODUCT_NAME);

        restLineItemMockMvc.perform(put("/api/lineItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lineItem)))
                .andExpect(status().isOk());

        // Validate the LineItem in the database
        List<Lineitem> lineItems = lineItemRepository.findAll();
        assertThat(lineItems).hasSize(databaseSizeBeforeUpdate);
        Lineitem testLineItem = lineItems.get(lineItems.size() - 1);
        assertThat(testLineItem.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void deleteLineItem() throws Exception {
        // Initialize the database
        lineItemRepository.saveAndFlush(lineItem);

		int databaseSizeBeforeDelete = lineItemRepository.findAll().size();

        // Get the lineItem
        restLineItemMockMvc.perform(delete("/api/lineItems/{id}", lineItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lineitem> lineItems = lineItemRepository.findAll();
        assertThat(lineItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
