package io.snyk.plugins.artifactory.scanner;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.snyk.plugins.artifactory.configuration.ConfigurationModule;
import io.snyk.sdk.api.v1.SnykClient;
import io.snyk.sdk.model.TestResult;
import org.artifactory.fs.FileLayoutInfo;
import org.slf4j.Logger;
import retrofit2.Response;

import static io.snyk.plugins.artifactory.configuration.PluginConfiguration.API_ORGANIZATION;
import static org.slf4j.LoggerFactory.getLogger;

class PythonScanner {

  private static final Logger LOG = getLogger(PythonScanner.class);

  private final ConfigurationModule configurationModule;
  private final SnykClient snykClient;

  PythonScanner(ConfigurationModule configurationModule, SnykClient snykClient) {
    this.configurationModule = configurationModule;
    this.snykClient = snykClient;
  }

  TestResult scan(FileLayoutInfo fileLayoutInfo) {
    String organization = configurationModule.getProperty(API_ORGANIZATION);

    TestResult testResult = null;
    try {
      Response<TestResult> response = snykClient.testPip(fileLayoutInfo.getModule(),
                                                         fileLayoutInfo.getBaseRevision(),
                                                         organization).execute();
      if (response.isSuccessful() && response.body() != null) {
        testResult = response.body();
        String responseAsText = new ObjectMapper().writeValueAsString(response.body());
        LOG.debug("testPip response: {}", responseAsText);
      }

    } catch (IOException ex) {
      LOG.error("Could not test python artifact: {}", fileLayoutInfo, ex);
    }

    return testResult;
  }
}
