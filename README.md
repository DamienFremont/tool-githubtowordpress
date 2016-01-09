A simple batch to convert src to wordpress source:


```java

  @Test
  public void testMain() throws IOException {
    String[] args = new String[] { //
        "-sourceFolder", "C:/Users/damien/git/blog/20151119-javaee-angularjs-bootstrap-auth_basic", //
        "-targetFile", "target/towordpress.txt", //
        "-excludeFiles", "target,.settings,.project,.classpath,README.md"};
    Job.main(args);
  }
```
  