jui
====
jui is a UI Automation Framework built on Selenium Java Client and other open sources. The purpose is to run the UI automation testing for web applications.

What does jui offer?
--

**Ability run test on one or multiple browsers**

in `config.properties`

```
browsers=chrome
```
or

```
browsers=chrome:firefox
```

**Ability generate data test from a defined template or parse from a file**

Generating random data tests is preferred in development/testing environments when the input is meaningless.
Centralizing data test and test steps in one place will increase transparency and reduce debugging time.

E.g.

```java
	String[] headers = { "name", "phone", "subject"};
	String[] regexes = { "[a-z]{10}", "[0-9]{10}", "[a-z]{10}"};
		
	DataDriven data = new DataDriven().withGenRegex(titles, regexes);
	Form formData = data.randomData();
		
	formData.getField("name").getValue();
	formData.getField("phone").getValue();
	formData.getField("subject").getValue();
		
```

Parsing data tests from files is preferred in staging/production environments when the input is historic and binding.
Separating data test and test steps will increase the flexibility of data format changes reduce modifying source code.

E.g.

```java
	String filePath = "input.csv";
	DataDriven data = new DataDriven().parse(filePath);
		
	List<Form> formDatas = data.getAll();
		
	formDatas.forEach((formData) -> {
		formData.getField("name").getValue());
		formData.getField("phone").getValue());
		formData.getField("subject").getValue());
	});
```

**WebElement interactions and more**

Finally, the core of UI Automation Framework is providing many customized methods for interacting with UI Elements (click, scroll, find, wait..) that will be used as steps in the test cases.

What's left in the TODO list?
--

[TODO - stability] improve the stability of test execution.

[TODO - test report] adding cucumber and define test as BDD, generate reports.

[TODO - screenshot] adding logging and screenshot when the test failed.
