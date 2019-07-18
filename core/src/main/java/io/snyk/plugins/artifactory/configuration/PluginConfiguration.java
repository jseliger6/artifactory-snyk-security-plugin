package io.snyk.plugins.artifactory.configuration;

public enum PluginConfiguration implements Configuration {
  // general settings
  API_URL("snyk.api.url", ""),
  API_TOKEN("snyk.api.token", ""),
  API_ORGANIZATION("snyk.api.organization", ""),

  // scanner module
  SCANNER_VULNERABILITY_THRESHOLD("snyk.scanner.vulnerability.threshold", "low"),
  SCANNER_LICENSE_THRESHOLD("snyk.scanner.license.threshold", "low");

  private final String propertyKey;
  private final String defaultValue;

  PluginConfiguration(String propertyKey, String defaultValue) {
    this.propertyKey = propertyKey;
    this.defaultValue = defaultValue;
  }

  @Override
  public String propertyKey() {
    return propertyKey;
  }

  @Override
  public String defaultValue() {
    return defaultValue;
  }
}
