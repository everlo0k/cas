package org.apereo.cas.config;

import lombok.val;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link CasPersonDirectoryConfigurationMergeAggregationTests}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@SpringBootTest(classes = {
    RefreshAutoConfiguration.class,
    CasPersonDirectoryConfiguration.class,
    CasCoreUtilConfiguration.class
}, properties = {
    "cas.authn.attributeRepository.stub.attributes.uid=cas",
    "cas.authn.attributeRepository.stub.attributes.givenName=apereo-cas",
    "cas.authn.attributeRepository.stub.attributes.eppn=casuser",

    "cas.authn.attributeRepository.groovy[0].location=classpath:/GroovyAttributeRepository.groovy",
    "cas.authn.attributeRepository.groovy[0].order=1",

    "cas.authn.attributeRepository.json[0].location=classpath:/json-attribute-repository.json",
    "cas.authn.attributeRepository.json[0].order=2",

    "cas.authn.attributeRepository.aggregation=merge",
    "cas.authn.attributeRepository.merger=multivalued"
})
public class CasPersonDirectoryConfigurationMergeAggregationTests {
    @Autowired
    @Qualifier("aggregatingAttributeRepository")
    private IPersonAttributeDao aggregatingAttributeRepository;

    @Test
    public void verifyOperation() {
        assertNotNull(aggregatingAttributeRepository);
        val person = aggregatingAttributeRepository.getPerson("casuser", IPersonAttributeDaoFilter.alwaysChoose());
        assertNotNull(person);
        assertNotNull(person.getAttributeValue("uid"));
        assertNotNull(person.getAttributeValue("givenName"));
        assertEquals(2, person.getAttributeValues("eppn").size());
        assertNotNull(person.getAttributeValue("username"));
        assertNotNull(person.getAttributeValue("likes"));
        assertNotNull(person.getAttributeValue("oldName"));
        assertNotNull(person.getAttributeValue("newName"));
        assertEquals(5, person.getAttributeValues("id").size());
    }
}
