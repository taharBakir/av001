package com.av001.web.rest;

import com.av001.Application;
import com.av001.domain.ShippingAddress;
import com.av001.repository.ShippingAddressRepository;
import com.av001.repository.search.ShippingAddressSearchRepository;

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
 * Test class for the ShippingAddressResource REST controller.
 *
 * @see ShippingAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShippingAddressResourceTest {

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
    private ShippingAddressRepository shippingAddressRepository;

    @Inject
    private ShippingAddressSearchRepository shippingAddressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restShippingAddressMockMvc;

    private ShippingAddress shippingAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShippingAddressResource shippingAddressResource = new ShippingAddressResource();
        ReflectionTestUtils.setField(shippingAddressResource, "shippingAddressRepository", shippingAddressRepository);
        ReflectionTestUtils.setField(shippingAddressResource, "shippingAddressSearchRepository", shippingAddressSearchRepository);
        this.restShippingAddressMockMvc = MockMvcBuilders.standaloneSetup(shippingAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        shippingAddress = new ShippingAddress();
        shippingAddress.setAddress1(DEFAULT_ADDRESS1);
        shippingAddress.setAddress2(DEFAULT_ADDRESS2);
        shippingAddress.setVille(DEFAULT_VILLE);
        shippingAddress.setCodePostal(DEFAULT_CODE_POSTAL);
        shippingAddress.setPays(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void createShippingAddress() throws Exception {
        int databaseSizeBeforeCreate = shippingAddressRepository.findAll().size();

        // Create the ShippingAddress

        restShippingAddressMockMvc.perform(post("/api/shippingAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
                .andExpect(status().isCreated());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddresss = shippingAddressRepository.findAll();
        assertThat(shippingAddresss).hasSize(databaseSizeBeforeCreate + 1);
        ShippingAddress testShippingAddress = shippingAddresss.get(shippingAddresss.size() - 1);
        assertThat(testShippingAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testShippingAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testShippingAddress.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testShippingAddress.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testShippingAddress.getPays()).isEqualTo(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void getAllShippingAddresss() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get all the shippingAddresss
        restShippingAddressMockMvc.perform(get("/api/shippingAddresss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shippingAddress.getId().intValue())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
                .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL.toString())))
                .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS.toString())));
    }

    @Test
    @Transactional
    public void getShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shippingAddresss/{id}", shippingAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shippingAddress.getId().intValue()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL.toString()))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingAddress() throws Exception {
        // Get the shippingAddress
        restShippingAddressMockMvc.perform(get("/api/shippingAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

		int databaseSizeBeforeUpdate = shippingAddressRepository.findAll().size();

        // Update the shippingAddress
        shippingAddress.setAddress1(UPDATED_ADDRESS1);
        shippingAddress.setAddress2(UPDATED_ADDRESS2);
        shippingAddress.setVille(UPDATED_VILLE);
        shippingAddress.setCodePostal(UPDATED_CODE_POSTAL);
        shippingAddress.setPays(UPDATED_PAYS);

        restShippingAddressMockMvc.perform(put("/api/shippingAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shippingAddress)))
                .andExpect(status().isOk());

        // Validate the ShippingAddress in the database
        List<ShippingAddress> shippingAddresss = shippingAddressRepository.findAll();
        assertThat(shippingAddresss).hasSize(databaseSizeBeforeUpdate);
        ShippingAddress testShippingAddress = shippingAddresss.get(shippingAddresss.size() - 1);
        assertThat(testShippingAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testShippingAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testShippingAddress.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testShippingAddress.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testShippingAddress.getPays()).isEqualTo(UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void deleteShippingAddress() throws Exception {
        // Initialize the database
        shippingAddressRepository.saveAndFlush(shippingAddress);

		int databaseSizeBeforeDelete = shippingAddressRepository.findAll().size();

        // Get the shippingAddress
        restShippingAddressMockMvc.perform(delete("/api/shippingAddresss/{id}", shippingAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ShippingAddress> shippingAddresss = shippingAddressRepository.findAll();
        assertThat(shippingAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
