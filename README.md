Koubai
=======

An android library for coloring Google Maps Polylines.



Download
--------
```groovy
compile 'com.daidaiiro:kubai:1.0.0'
```
or Maven:
```xml
<dependency>
  <groupId>com.daidaiiro</groupId>
  <artifactId>kubai</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

How do I use Kubai?
-------------------
Easy as 1-2-3.

```java
Kubai kubai = new Kubai(googleMap, points, Color.RED, Color.GREEN);
kubai.drawGradient();
```


License
--------

    Copyright 2013 Square, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
