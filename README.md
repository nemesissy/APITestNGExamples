# API Validation with Rest Assured / TestNG

### App Under Test ###

https://datausa.io/

(2022/08/31)

### Pre-requisites ###

* I'm using IJ to create
* I'm using GRADLE to build
* I'm usinf TestNG
* I'm using Rest Assured

### Run all Tests

$ ./gradlew test

### Run a  singe test
$ ./gradlew test --tests "com.nemesissy.tests.States"

### Location of the HTML report
./build/reports/tests/test/index.html

### Usages
* Object Counts
* JSON Validation
* Interrogating elements in an array
* Incrementing over data providers
* Standard Hamcrest Matchers
* Custom Hamcrest Matchers
