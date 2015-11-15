package com.av001.web.rest;

import com.av001.Application;
import com.av001.domain.InvoiceAddress;
import com.av001.repository.InvoiceAddressRepository;
import com.av001.repository.search.InvoiceAddressSearchRepository;

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
 * Test class for the InvoiceAddressResource REST controller.
 *
 * @see InvoiceAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InvoiceAddressResourceTest {

    private static final String DEFAULT_ADDRESS1 = "AAAAA";
    private static final String UPDATED_ADDRESS1 = "BBBBB";
    private static final String DEFAULT_ADDRESS2 = "AAAAA";
    private static final String UPDATED_ADDRESS2 = "BBBBB";
    private static final String DEFAULT_VILLE = "AAAAA";
    private static final String UPDATED_VILLE = "BBBBB";
    private static final String DEFAULT_CODE_POSTAL = "AAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBB";
    private static final String DEFAULT_PAYS = "AAAAA";
    private static final String UPDATED_PAYS = "BBBBB";

    @Inject
    private InvoiceAddressRepository invoiceAddressRepository;

    @Inject
    private InvoiceAddressSearchRepository invoiceAddressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInvoiceAddressMockMvc;

    private InvoiceAddress invoiceAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvoiceAddressResource invoiceAddressResource = new InvoiceAddressResource();
        ReflectionTestUtils.setField(invoiceAddressResource, "invoiceAddressRepository", invoiceAddressRepository);
        ReflectionTestUtils.setField(invoiceAddressResource, "invoiceAddressSearchRepository", invoiceAddressSearchRepository);
        this.restInvoiceAddressMockMvc = MockMvcBuilders.standaloneSetup(invoiceAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        invoiceAddress = new InvoiceAddress();
        invoiceAddress.setAddress1(DEFAULT_ADDRESS1);
        invoiceAddress.setAddress2(DEFAULT_ADDRESS2);
        invoiceAddress.setVille(DEFAULT_VILLE);
        invoiceAddress.setCodePostal(DEFAULT_CODE_POSTAL);
        invoiceAddress.setPays(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void createInvoiceAddress() throws Exception {
        int databaseSizeBeforeCreate = invoiceAddressRepository.findAll().size();

        // Create the InvoiceAddress

        restInvoiceAddressMockMvc.perform(post("/api/invoiceAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceAddress)))
                .andExpect(status().isCreated());

        // Validate the InvoiceAddress in the database
        List<InvoiceAddress> invoiceAddresss = invoiceAddressRepository.findAll();
        assertThat(invoiceAddresss).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceAddress testInvoiceAddress = invoiceAddresss.get(invoiceAddresss.size() - 1);
        assertThat(testInvoiceAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testInvoiceAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testInvoiceAddress.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testInvoiceAddress.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testInvoiceAddress.getPays()).isEqualTo(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void getAllInvoiceAddresss() throws Exception {
        // Initialize the database
        invoiceAddressRepository.saveAndFlush(invoiceAddress);

        // Get all the invoiceAddresss
        restInvoiceAddressMockMvc.perform(get("/api/invoiceAddresss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceAddress.getId().intValue())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
                .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())))
                .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())));
    }

    @Test
    @Transactional
    public void getInvoiceAddress() throws Exception {
        // Initialize the database
        invoiceAddressRepository.saveAndFlush(invoiceAddress);

        // Get the invoiceAddress
        restInvoiceAddressMockMvc.perform(get("/api/invoiceAddresss/{id}", invoiceAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invoiceAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceAddress() throws Exception {
        // Get the invoiceAddress
        restInvoiceAddressMockMvc.perform(get("/api/invoiceAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceAddress() throws Exception {
        // Initialize the database
        invoiceAddressRepository.saveAndFlush(invoiceAddress);

		int databaseSizeBeforeUpdate = invoiceAddressRepository.findAll().size();

        // Update the invoiceAddress
        invoiceAddress.setAddress1(UPDATED_ADDRESS1);
        invoiceAddress.setAddress2(UPDATED_ADDRESS2);
        invoiceAddress.setVille(UPDATED_VILLE);
        invoiceAddress.setCodePostal(UPDATED_CODE_POSTAL);
        invoiceAddress.setPays(UPDATED_PAYS);

        restInvoiceAddressMockMvc.perform(put("/api/invoiceAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invoiceAddress)))
                .andExpect(status().isOk());

        // Validate the InvoiceAddress in the database
        List<InvoiceAddress> invoiceAddresss = invoiceAddressRepository.findAll();
        assertThat(invoiceAddresss).hasSize(databaseSizeBeforeUpdate);
        InvoiceAddress testInvoiceAddress = invoiceAddresss.get(invoiceAddresss.size() - 1);
        assertThat(testInvoiceAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testInvoiceAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testInvoiceAddress.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testInvoiceAddress.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testInvoiceAddress.getPays()).isEqualTo(UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void deleteInvoiceAddress() throws Exception {
        // Initialize the database
        invoiceAddressRepository.saveAndFlush(invoiceAddress);

		int databaseSizeBeforeDelete = invoiceAddressRepository.findAll().size();

        // Get the invoiceAddress
        restInvoiceAddressMockMvc.perform(delete("/api/invoiceAddresss/{id}", invoiceAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InvoiceAddress> invoiceAddresss = invoiceAddressRepository.findAll();
        assertThat(invoiceAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
