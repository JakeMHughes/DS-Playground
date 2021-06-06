# ds
## core
### append
*Given an array and a value, will append the value to the end of the array*

* Params:
    - 1: array: Array 
    - 2: value: Any

Example:
```json5
ds.append([1,2,3], 4)
```
Output:
```json5
[1,2,3,4]
```
---
### combine
*Will combine the two values given. Some values will auto-coerce for instance, 
the number 5 will auto coerce to the string "5"*

* Params:
    - 1: first: Any
    - 2: second: typeOf(container)

Example:
```json5
{
    string: ds.combine("Hello ", "World"),
    number: ds.combine(5, 7),
    auto1: ds.combine("Hello ", 5),
    auto2: ds.combine(5, "10"),
    array: ds.combine([1,2], [3,4]),
    obj: ds.combine({a:1}, {b:2})
}
```
Output:
```json5
{
  "string": "Hello World",
  "number": "57",
  "auto1": "Hello 5",
  "auto2": "510",
  "array": [1,2,3,4],
  "obj": {
    "a": 1,
    "b": 2
  }
}
```
---
### contains
*Returns true if the array contains the given value or the string contains the value sub string*

* Params
    - 1: container: Array | String
    - 2: value: Any
    
Example:
```json5
ds.contains([1,2,3], 1)
```
Output:
```json5
true
```
---
### distinctBy
*Returns a new container object that only has items that are a unique result from the function.*

* Params
    - 1: container: Array | Object
    - 2: funct: Function(item,index) | Function(value,key)

Example:
```json5
{
    array: ds.distinctBy([1,2,3,4,5,4,3,2,1], function(item,index) item),
    obj: ds.distinctBy({a:1,b:2, c:1}, function(value,key) value)
}
```
Output:
```json5
{
  "array": [1,2,3,4,5],
  "obj": {
    "a": 1,
    "b": 2
  }
}
```
---
### endsWith
*Returns true or false if the given string ends with the substring. Ignores casing.*

* Params
    - 1: str: String
    - 2: sub: String

Example:
```json5
ds.endsWith("Hello World", "world")
```
Output:
```json5
true
```
---
### entriesOf
*Returns an array of objects describing each key value pair.*

* Params
    - 1: obj: Object

Example:
```json5
ds.entriesOf({testK:  "testV"})
```
Output:
```json5
[
  {
    "key": "testK",
    "value": "testV"
  }
]
```
---
### filter
*filters an array depending on the outcome of the provided function.*

* Parms:
    - 1: array: Array
    - 2: Function(item, index): Boolean

Example:
```json5
ds.filter([1,2,3,4], function(item,index) item > 2)
```
Output:
```json5
[3,4]
```
---
### filterObject
*filters an object depending on the outcome of the provided function.*

* Parms:
    - 1: obj: Object
    - 2: Function(value, key, index): Boolean

Example:
```json5
ds.filterObject({a:1,b:2}, function(value,key,index) value >1)
```
Output:
```json5
{"b": 2}
```
---
### find
*Returns an array containing the location of the value occurance*

* Parms:
    - 1: container: String | Array
    - 2: any: Any

Example:
```json5
{
    string: ds.find("Hello World", "World"),
    array: ds.find([1,2,3,4], 3)
}
```
Output:
```json5
{
  "string": [6],
  "array": [2]
}
```
---
### flatMap
*Given an array of arrays, creates a flat array using the outcome of the provided function*

* Parms:
    - 1: arr: Array<Array>
    - 2: Function(item,index)

Example:
```json5
ds.flatMap([[1,2,3,4],[5,6,7,8]], function(item,index) item)
```
Output:
```json5
[1,2,3,4,5,6,7,8]
```
---
### flatten
*Given an array of arrays, creates a flat array*

* Parms:
    - 1: arr: Array<Array>

Example:
```json5
ds.flatten([[1,2,3,4],[5,6,7,8]])
```
Output:
```json5
[1,2,3,4,5,6,7,8]
```
---
### foldLeft
*Iterates over an array, applying the function the previous result.*

* Params:
    - 1: arr: Array
    - 2: Function
    - 3: init: Any

Example:
```json5
ds.foldLeft([1,2,3,4], function(curr,prev) curr * prev, 1)
```
Output:
```json5
24
/*
  1 * 1 = 1
  2 * 1 = 2
  3 * 2 = 6
  4 * 6 = 24
*/
```
---
### foldRight
*Iterates backwards over an array, applying the function the previous result.*

* Params:
    - 1: arr: Array
    - 2: Function
    - 3: init: Any

Example:
```json5
ds.foldRight([1,2,3,4], function(curr,prev) curr * prev, 1)
```
Output:
```json5
24
/*
  4 * 1 = 4 // 1 in this case is the initial value
  3 * 4 = 12
  2 * 12 = 24
  1 * 24 = 24
*/
```
---
### groupBy
*Groups the items in a container into an object based on the results of the function.*

* Parms:
    - 1: container: Array | Object
    - 2: funct: Function(item,index) | Function(value,key)

Example:
```json5
{
    array: ds.groupBy(["a","b","a"], function(item,index) item ),
    obj: ds.groupBy({a:"Alpha", b:"Bravo", c: "Alpha"}, function(value,key) value)
}
```
Output:
```json5
{
  "array": {
    "a": ["a","a"],
    "b": ["b"]
  },
  "obj": {
    "Alpha": {
      "a": "Alpha",
      "c": "Alpha"
    },
    "Bravo": {
      "b": "Bravo"
    }
  }
}
```
---
### isArray
*Accepts any given value and checks if it is of type array*

* Params:
    - 1: any: Any

Example:
```json5
ds.isArray([])
```
Output:
```json5
true
```
---
### isBlank
*Checks if a string is blank. Also returns true if null*

* Parms:
    - 1: str: String

Example:
```json5
{
    str1: ds.isBlank("     "),
    str2: ds.isBlank(""),
    'null': ds.isBlank(null)
}
```
Output:
```json5
{
  "str1": true,
  "str2": true,
  "null": true
}
```
---
### isBoolean
*Accepts any given value and checks if it is of type bool*

* Params:
    - 1: any: Any

Example:
```json5
ds.isBoolean(true)
```
Output:
```json5
true
```
---
### isDecimal
*Checks that the input number is a decimal number. Trailing zeros do not count*

* Params:
    - 1: num: Number

Example:
```json5
{
    a: ds.isDecimal(2),
    b: ds.isDecimal(2.0),
    c: ds.isDecimal(2.1),
}
```
Output:
```json5
{
  "a": false,
  "b": false,
  "c": true
}
```
---
### isEmpty
*Checks if a given value is empty. Does not ignore white space if string. Returns true if null.*

* Params:
    - 1: any: Any

Example:
```json5
{
    'null': ds.isEmpty(null),
    str: ds.isEmpty("    "),
    array: ds.isEmpty([]),
    obj: ds.isEmpty({})
}
```
Output:
```json5
{
  "null": true,
  "str": false,
  "array": true,
  "obj": true
}
```
---
### isEven
*Checks that the input number is an even number.*

* Params:
    - 1: num: Number

Example:
```json5
ds.isEven(2.0)
```
Output:
```json5
true
```
---
### isFunction
*Accepts any given value and checks if it is of type function*

* Params:
    - 1: any: Any

Example:
```json5
ds.isFunction(function() "5")
```
Output:
```json5
true
```
---
### isInteger
*Checks that the input number is an integer.*

* Params:
    - 1: num: Number

Example:
```json5
ds.isInteger(2.0)
```
Output:
```json5
true
```
---
### isNumber
*Accepts any given value and checks if it is of type number*

* Params:
    - 1: any: Any

Example:
```json5
ds.isNumber(5)
```
Output:
```json5
true
```
---
### isObject
*Accepts any given value and checks if it is of type object*

* Params:
    - 1: any: Any

Example:
```json5
ds.isObject({})
```
Output:
```json5
true
```
---
### isOdd
*Checks that the input number is an odd number.*

* Params:
    - 1: num: Number

Example:
```json5
ds.isOdd(2.0)
```
Output:
```json5
false
```
---
### isString
*Accepts any given value and checks if it is of type string*

* Params:
    - 1: any: Any

Example:
```json5
ds.isString("")
```
Output:
```json5
true
```
---
### joinBy
*Joins an entire array into a string with the provided seperator.*

* Params:
    - 1: arr: Array
    - 2: str: String

Example:
```json5
ds.joinBy([1,2,3,4,5], "-")
```
Output:
```json5
"1-2-3-4-5"
```
---
### keysOf
*Returns an array of all the key names in a given object.*

* Params:
    - 1: obj: Object

Example:
```json5
ds.keysOf({a:1,b:2,c:3})
```
Output:
```json5
["a","b","c"]
```
---
### lower
*Converts a string to all lower case characters.*

* Params:
    - 1: str: String

Example:
```json5
ds.upper("HeLlO wOrLd")
```
Output:
```json5
"hello world"
```
---
### map
*Loops through all items in a given array applying the function to each and returning a new array
containing each result. Returns null if array is null.*

* Params:
    - 1: array: Array
    - 2: Function(item,index)

Example:
```json5
ds.map([5,4,3,2,1], function(item,index) index > item)
```
Output:
```json5
[false,false,false,true,true]
```
---
### mapEntries
*Description*

* Params:
    - 1: obj: Object
    - 2: Function(value,key,index)

Example:
```json5
ds.mapEntries({a:1,b:2,c:3}, function(val,key,indx) {[key]:val})
```
Output:
```json5
[
  {"a": 1},
  {"b": 2},
  {"c": 3}
]
```
---
### mapObject
*Description*

* Params:
    - 1: obj: Object
    - 2: Function(value,key,index)

Example:
```json5
ds.mapObject({a:1,b:2,c:3}, function(value,key,index) {[key]:value})
```
Output:
```json5
{
  "a": 1,
  "b": 2,
  "c": 3
}
```
---
### match
*Executes the regex expression against the string and returns an array with the match groups.*

* Params:
    - 1: str: String
    - 2: regex: String

Example:
```json5
ds.match("test@server.com", "(.*)@(.*)(.com)")
```
Output:
```json5
[
  "test@server.com",
  "test",
  "server",
  ".com"
]
```
---
### matches
*Executes the regex expression against the string and returns true or false if the expression matches the input*

* Params:
    - 1: str: String
    - 2: regex: String

Example:
```json5
ds.matches("test@server.com", "(.*)@(.*)(.com)")
```
Output:
```json5
true
```
---
### max
*Returns the max value in an array*

* Params:
    - 1: array: Array<Number>

Example:
```json5
ds.max([5,2,7,3])
```
Output:
```json5
7
```
---
### maxBy
*Returns the max function result value in an array*

* Params:
    - 1: array: Array
    - 2: Function(item)
    
Example:
```json5
ds.maxBy([{a:5},{a:7},{a:3}], function(item) item.a)
```
Output:
```json5
{
  "a": 7
}
```
---
### min
*Returns the min value in an array*

* Params:
    - 1: array: Array<Number>

Example:
```json5
ds.min([5,2,7,3])
```
Output:
```json5
2
```
---
### minBy
*Returns the min function result value in an array*

* Params:
    - 1: array: Array
    - 2: Function(item)
    
Example:
```json5
ds.minBy([{a:5},{a:7},{a:3}], function(item) item.a)
```
Output:
```json5
{
  "a": 3
}
```
---
### orderBy
*Description*

possible issue, try 
`ds.orderBy([{a:5}, {a:7}, {a:3}], function(value,key) value)`

Example:
```json5
ds.???
```
Output:
```json5
???
```
---
### parseDouble
*Will parse a string containing a number. Will chop off any trailing zeros*

* Params:
    - 1: str: String

Example:
```json5
ds.parseDouble("2.5")
```
Output:
```json5
2.5
```
---
### parseHex
*Parses a hex value given as a string and returns its decimal value*

* Params:
    - 1: str: String

Example:
```json5
ds.parseHex("F")
```
Output:
```json5
15
```
---
### parseInt
*Parses a int value given as a string and returns its decimal value*

* Params:
    - 1: str: String

Example:
```json5
ds.parseInt("50")
```
Output:
```json5
50
```
---
### parseOctal
*Parses a octal value given as a string and returns its decimal value*

* Params:
    - 1: str: String

Example:
```json5
ds.parseOctal("107136")
```
Output:
```json5
36446
```
---
### prepend
*Given an array and a value, will append the value to the beginning of the array*

* Params:
    - 1: array: Array 
    - 2: value: Any

Example:
```json5
ds.prepend([1,2,3], 4)
```
Output:
```json5
[4,1,2,3]
```
---
### range
*Returns an array with the numbers from the start to the end of the range, inclusive.*

* Params:
    - 1: begin: Number
    - 2: end: Number

Example:
```json5
ds.range(0, 3)
```
Output:
```json5
[0,1,2,3]
```
---
### read
*Reads a string value as the given mimetype.*

* Params:
    - 1: data: String
    - 2: mimeType: String
    - 3: params: Object

Example:
```json5
ds.read("{\"a\":1}", "application/json",{})
```
Output:
```json5
{
  "a": 1
}
```
---
### readUrl
*Description*

Example:
```json5
ds.readUrl("http://httpbin.org/get")
```
Output:
```json5
{
  "args": {},
  "headers": {
    "Accept": "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2",
    "Host": "httpbin.org",
    "User-Agent": "Java/11.0.8",
    "X-Amzn-Trace-Id": "Root=1-5f5e7b25-75b36a4485370351de6f58fe"
  },
  "origin": "255.255.255.255",
  "url": "http://httpbin.org/get"
}
```
---
### remove
*given either an array or object, will remove a specific value. If an object is given, 
the value must be the string value of a key*

* Params:
    - 1: container: Array | Object
    - 2: value: Any | String

Example:
```json5
{
  array: ds.remove([1,2,3,4], 3),
  obj: ds.remove({a:1,b:2}, "b")
}
```
Output:
```json5
{
  "array": [1,2,4],
  "obj": {"a": 1}
}
```
---
### removeMatch
*Given an array or an object and a value of the same type, will remove the matching values. 
If an object is used, both key and value must match.*

* Params:
    - 1: container: Array | Object
    - 2: value: typeOf(container)

Example:
```json5
{
  array: ds.removeMatch([1,2,3,4], [1,4]),
  obj: ds.removeMatch(ds.removeMatch({a:1,b:2}, {a:1,b:3}))
}
```
Output:
```json5
{
  "array": [2,3],
  "obj": {"b": 2} 
}
```
---
### replace
*Replaces the matching regex with the replacement string.*

* Params:
    - 1: str: String
    - 2: regex: String
    - 3: replace: String

Example:
```json5
ds.replace("Hello World", "Hello", "Goodbye")
```
Output:
```json5
"Goodbye World"
```
---
### reverse
*Given an array or object, reverses the order of the elements.*

* Params:
    - 1: container: Array | Object

Example:
```json5
{
  array: ds.reverse([1,2,3]),
  obj: ds.reverse({a:1,b:2,c:3})
}
```
Output:
```json5
{
  "array": [3,2,1],
  "obj": {"c": 3,"b": 2,"a": 1}
}
```
---
### scan
*Description*

Example:
```json5
ds.scan("test@server.com", "(.*)@(.*)(.com)")
```
Output:
```json5
[
  [
    "test@server.com",
    "test",
    "server",
    ".com"
  ]
]
```
---
### sizeOf
*Returns the size of an object.*

* Params:
    - 1: any: Any

Example:
```json5
{
    array: ds.sizeOf([1,2]),
    object: ds.sizeOf({a:2}),
    'null': ds.sizeOf(null),
    'function': ds.sizeOf(function(a,b,c) 1),
    string: ds.sizeOf("x")
}
```
Output:
```json5
{
  "array": 2,
  "object": 1,
  "null": 0,
  "function": 3,
  "string": 1
}
```
---
### splitBy
*Splits a string into an array based on the matching regex*

* Params:
    - 1: str: String
    - 2: regex: String
    
Example:
```json5
ds.splitBy("Hello World", " ")
```
Output:
```json5
["Hello","World"]
```
---
### startsWith
*Checks if a given string starts with a sub string. Ignores casing.*

* Params:
    - 1: str: String
    - 2: sub: String

Example:
```json5
ds.startsWith("Hello World", "hello")
```
Output:
```json5
true
```
---
### trim
*Removes leading and trailing spaces in a string*

* Params:
    - 1: str: String

Example:
```json5
ds.trim("      Hello World       ")
```
Output:
```json5
"Hello World"
```
---
### typeOf
*Returns a string describing the type of object the input is.*

* Params:
    - 1: any: Any

Example:
```json5
{
    string: ds.typeOf(""),
    bool: ds.typeOf(true),
    'null': ds.typeOf(null),
    number: ds.typeOf(0),
    'function': ds.typeOf(function() 1),
    array: ds.typeOf([]),
    object: ds.typeOf({})
}
```
Output:
```json5
{
  "string": "string",
  "bool": "boolean",
  "null": "null",
  "number": "number",
  "function": "function",
  "array": "array",
  "object": "object"
}
```
---
### unzip
*Unzips an array of arrays and creats a new array of arrays based on their index in the array.
so `[[1,2],[1,2]]` will create an array using the first index `[1,1]` and an aarray using the second index
`[2,2]`*

* Params:
    - 1: array: Array

Example:
```json5
ds.unzip([[1,2],[1,2]])
```
Output:
```json5
[[1,1],[2,2]]
```
---
### upper
*Converts a string to all uppercase characters*

* Params:
    - 1: str: String

Example:
```json5
ds.upper("HeLlO wOrLd")
```
Output:
```json5
"HELLO WORLD"
```
---
### uuid
*Generates random alphanumeric uuid*

Example:
```json5
ds.uuid
```
Output:
```json5
"rpjs5q3t-xuhs-rmko-kurs-1dhms27oezr3"
```
---
### valuesOf
*Given an object, returns an array of the values inside the object*

* Params:
    - 1: obj: Object

Example:
```json5
ds.valuesOf({a:1,b:2})
```
Output:
```json5
[1,2]
```
---
### write
*Converts the value to a string.*

* Params:
    - 1: data: Object | Array
    - 2: mimeType: String
    - 3: params: Object

Example:
```json5
ds.write({"a":1}, "application/json", {})
```
Output:
```json5
"{\"a\":1}"
```
---
### zip
*Accepts two arrays and combines them into one using elements with matching index's*

* Params:
    - 1: arr1: Array
    - 2: arr2: Array

Example:
```json5
ds.zip([1,2],[3,4,5])
```
Output:
```json5
[[1,3],[2,4]]
```
---
## crypto
### hash
*Hashes the value using the given algorithm. Possible algorithm values: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512*

* Params:
    - 1: value: String
    - 2: alg: String

Example:
```json5
ds.crypto.hash("Hello World", "MD5")
```
Output:
```json5
"b10a8db164e0754105b7a99be72e3fe5"
```
---
### hmac
*Hashes the value using the given key and algorithm. Possible algorithm values: HmacSHA1, HmacSHA256 or HmacSHA512*

* Params:
    - 1: value: String
    - 2: secret: String
    - 3: alg: String

Example:
```json5
ds.crypto.hmac("Hello World", "key", "HmacSHA1")
```
Output:
```json5
"cc24f1acdb06cf429bcf9861b6d708b6ec20a8fa"
```
---
## jsonpath
### select
*Evaluates JsonPath expression and returns the resulting JSON object. It uses the Jayway JsonPath implementation 
and fully supports JsonPath specification.*

* Params:
    - 1: json: Array | Object
    - 2: path: String

Example:
```json5
ds.jsonpath.select({message: "Hello World"},",.message")
```
Output:
```json5
["Hello World"]
```
---
## regex
### regexFullMatch
*Returns the capture groups of full matches.*

* Params:
    - 1: regex: String
    - 2: str: String

Example:
```json5
ds.regex.regexFullMatch("(Hello) ?(World)", "Hello World")
```
Output:
```json5
{
  "string": "Hello World",
  "captures": [
    "Hello",
    "World"
  ],
  "namedCaptures": {}
}
```
---
### regexGlobalReplace
*Replaces every occurance in the string where the pattern matches with the replacement string.*

* Params:
    - 1: str: String
    - 2: pattern: String
    - 3: replace: String

Example:
```json5
ds.regex.regexGlobalReplace("Hello World Hello", "Hello", "Goodbye")
```
Output:
```json5
"Goodbye World Goodbye"
```
---
### regexPartialMatch
*Returns the capture groups of each partial match.*

* Params:
    - 1: value:
    - 2: value:

Example:
```json5
ds.regex.regexPartialMatch("(Hell)o ?(World)", "Hello World")
```
Output:
```json5
{
  "string": "Hello World",
  "captures": [
    "Hell",
    "World"
  ],
  "namedCaptures": {}
}
```
---
### regexQuoteMeta
*Description*

* Params:
    - 1: value:
    - 2: value:

Example:
```json5
ds.???
```
Output:
```json5
???
```
---
### regexReplace
*Replaces every occurance in the string where the pattern matches with the replacement string.*

* Params:
    - 1: str: String
    - 2: pattern: String
    - 3: replace: String

Example:
```json5
ds.regex.regexGlobalReplace("Hello World Hello", "Hello", "Goodbye")
```
Output:
```json5
"Goodbye World Goodbye"
```
---
### regexScan
*Returns an array of arrays for match groups*

* Params:
    - 1: str: String
    - 2: regex: String

Example:
```json5
ds.scan("Hello World", "(Hello) (World)")
```
Output:
```json5
[
  [
    "Hello World",
    "Hello",
    "World"
  ]
]
```
---
## url
### decode
*Decodes a string value using the given encoding type*

* Params:
    - 1: data: String
    - 2: encoding: String

Example:
```json5
ds.url.decode("Hello+World", "UTF-8")
```
Output:
```json5
"Hello World"
```
---
### encode
*Encodes a string value using the given encoding type*

* Params:
    - 1: data: String
    - 2: encoding: String

Example:
```json5
ds.url.encode("Hello World", "UTF-8")
```
Output:
```json5
"Hello+World"
```
---
## math
### abs
*Returns the absolute value of a number.*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.abs(-1)
```
Output:
```json5
1
```
---
### acos
*Performs math acos operation*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.acos(1)
```
Output:
```json5
0
```
---
### asin
*Performs math asin operation*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.asin(1)
```
Output:
```json5
1.5707963267948966
```
---
### atan
*Performs math atan operation*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.atan(1)
```
Output:
```json5
0.7853981633974483
```
---
### avg
*Returns the average value of the array*

* Params:
    - 1: arr: Array<Number>

Example:
```json5
ds.math.avg([1,2,3])
```
Output:
```json5
2
```
---
### ceil
*Rounds number up*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.ceil(1.01)
```
Output:
```json5
2
```
---
### clamp
*Limits a value to a specific range*

* Params:
    - 1: x: Number
    - 2: minVal: Number
    - 3: maxVal: Number

Example:
```json5
ds.math.clamp(100, 0, 10)
```
Output:
```json5
10
```
---
### cos
*Performs math cos operation*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.cos(0)
```
Output:
```json5
1
```
---
### exp
*Returns the result of e to the power of num.*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.exp(2)
```
Output:
```json5
7.38905609893065
```
---
### exponent
*Returns the non-decimal portion of a logarithmic operation.*

exponent = (log(num)/log(2)) + 1 <br>

* Params:
    - 1: num: Number

Example:
```json5
ds.math.exponent(2)
```
Output:
```json5
2
```
---
### floor
*Rounds number down*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.floor(4.99)
```
Output:
```json5
4
```
---
### log
*Performs Math log operation*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.log(2)
```
Output:
```json5
0.6931471805599453
```
---
### mantissa
*Returns the decimal portion of a logarithmic operation.*

exponent = (log(num)/log(2)) + 1 <br>
mantissa = num * pow(2, -exponent)

* Params:
    - 1: num: Number

Example:
```json5
ds.math.mantissa(2)
```
Output:
```json5
0.5
```
---
### mod
*Performs modulo operation retuning how many times num1 can go into num2*

* Params:
    - 1: num1: Number
    - 2: num2: Number

Example:
```json5
ds.math.mod(2,4)
```
Output:
```json5
2
```
---
### pow
*Returns the value of num1 to the power of num2*

* Params:
    - 1: num1: Number
    - 2: num2: Number

Example:
```json5
ds.math.pow(2,2)
```
Output:
```json5
4
```
---
### random
*Get a random float value between 0 and 1*


Example:
```json5
ds.math.random
```
Output:
```json5
0.5826798074717513
```
---
### randomInt
*Get a random integer between 0 and the provided number inclusive.*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.randomInt(500)
```
Output:
```json5
467
```
---
### round
*Rounds the number to the nearest whole number.*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.round(2.5)
```
Output:
```json5
3
```
---
### sin
*Math sin operation.*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.sin(1)
```
Output:
```json5
0.8414709848078965
```
---
### sqrt
*Math square root operation*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.sqrt(4)
```
Output:
```json5
2
```
---
### sum
*Computes the sum of the array of numbers*

* Params:
    - 1: arr: Array<Number>

Example:
```json5
ds.math.sum([1,2,3])
```
Output:
```json5
6
```
---
### tan
*Math tan operation.*

* Params:
    - 1: num: Number

Example:
```json5
ds.math.tan(1)
```
Output:
```json5
1.5574077246549023
```
---
## arrays
### countBy
*Returns the number of items in the array that passes the condition of the function*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.countBy([1,2,3,4,5],function(item) item>2)
```
Output:
```json5
3
```
---
### divideBy
*Divides a single array into multiple arrrays limiting each one to the specified size*

* Params:
    - 1: arr: Array
    - 2: size: Number

Example:
```json5
ds.arrays.divideBy([1,2,3,4,5],2)
```
Output:
```json5
[
  [1,2],
  [3,4],
  [5]
]
```
---
### drop
*Removes every item in the array until the specified index is reached.*

* Params:
    - 1: arr: Array
    - 2: num: Number

Example:
```json5
ds.arrays.drop([1,2,3,4,5],3)
```
Output:
```json5
[4,5]
```
---
### dropWhile
*Removes every item in the array until the function returns a false result, then stops.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.dropWhile([1,2,3,4,5],function(item) item < 3)
```
Output:
```json5
[3,4,5]
```
---
### every
*Returns true if every value in the array returns true in the provided function.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.every([1,2,3,4,5],function(item) item >0)
```
Output:
```json5
true
```
---
### firstWith
*Returns the first value that passes the condition then stops.*

* Params:
    - 1: arr: Array
    - 2: Function(item,index)

Example:
```json5
ds.arrays.firstWith([1,2,3,4,5],function(item,index) item == index+1)
```
Output:
```json5
1
```
---
### indexOf
*Returns the current index of the matching value*

* Params:
    - 1: arr: Array
    - 2: value: Any

Example:
```json5
ds.arrays.indexOf([1,2,3,4,5],3)
```
Output:
```json5
2
```
---
### indexWhere
*Returns the first index where the function condition passes.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.indexWhere([1,2,3,4,5],function(item) item == 3)
```
Output:
```json5
2
```
---
### join
*Joins two arrays together based on the matching the returns of the provided functions.*

* Params:
    - 1: arrL: Array
    - 2: arrR: Array
    - 3: functL: Function(item)
    - 4: functR: Function(item)

Example:
```json5
ds.arrays.join(
    [{"id":1,"v":"a"},{"id":1,"v":"b"}],[{"id":1,"v":"c"}], 
    function(item) item.id,function(item) item.id
)
```
Output:
```json5
[
  {
    "r": {
      "id": 1,
      "v": "c"
    },
    "l": {
      "id": 1,
      "v": "a"
    }
  },
  {
    "r": {
      "id": 1,
      "v": "c"
    },
    "l": {
      "id": 1,
      "v": "b"
    }
  }
]
```
---
### leftJoin
*Description*

* Params:
    - 1: arrL: Array
    - 2: arrR: Array
    - 3: functL: Function(item)
    - 4: functR: Function(item)

Example:
```json5
ds.arrays.leftJoin(
    [{"id":1,"v":"a"},{"id":1,"v":"b"}],[{"id":1,"v":"c"}], 
    function(item) item.id,function(item) item.id
)
```
Output:
```json5
[
  {
    "r": {
      "id": 1,
      "v": "c"
    },
    "l": {
      "id": 1,
      "v": "a"
    }
  },
  {
    "r": {
      "id": 1,
      "v": "c"
    },
    "l": {
      "id": 1,
      "v": "b"
    }
  }
]
```
---
### outerJoin
*Description*

* Params:
    - 1: arrL: Array
    - 2: arrR: Array
    - 3: functL: Function(item)
    - 4: functR: Function(item)

Example:
```json5
ds.arrays.outerJoin(
    [{"id":1,"v":"a"},{"id":1,"v":"b"}],[{"id":1,"v":"c"}], 
    function(item) item.id,function(item) item.id
)
```
Output:
```json5
[
  {
    "r": {
      "id": 1,
      "v": "c"
    },
    "l": {
      "id": 1,
      "v": "a"
    }
  },
  {
    "r": {
      "id": 1,
      "v": "c"
    },
    "l": {
      "id": 1,
      "v": "b"
    }
  }
]
```
---
### partition
*Splits an array into two based on success and failures from the provided function.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.partition([1,2,3,4,5],function(item) item >3)
```
Output:
```json5
{
  "success": [4,5],
  "failure": [1,2,3]
}
```
---
### slice
*returns a subset of the array between the two provided indexs*

* Params:
    - 1: arr: Array
    - 2: start: Number
    - 3: end: Number

Example:
```json5
ds.arrays.slice([1,2,3,4,5],2,4)
```
Output:
```json5
[3,4]
```
---
### some
*Returns true if atleast one item in the array passes the function condition.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.some([1,2,3],function(item) item<2)
```
Output:
```json5
true
```
---
### splitAt
*splits an array into a left and right array based on the provided index*

* Params:
    - 1: arr: Array
    - 2: index: Number

Example:
```json5
ds.arrays.splitAt([1,2,3,4,5],3)
```
Output:
```json5
{
  "r": [4,5],
  "l": [1,2,3]
}
```
---
### splitWhere
*splits an array into a left and right array based on the first index that returns true for the provided function.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.splitWhere([1,2,3,4,5],function(item) item >3)
```
Output:
```json5
{
  "r": [4,5],
  "l": [1,2,3]
}
```
---
### sumBy
*Calculates the sum by the function provided value*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.sumBy([1,2,3,4,5],function(item) item)
```
Output:
```json5
15
```
---
### take
*Takes all values from the array up to the provided index*

* Params:
    - 1: arr: Array
    - 2: index: Number

Example:
```json5
ds.arrays.take([1,2,3,4],3)
```
Output:
```json5
[1,2,3]
```
---
### takeWhile
*Takes all items from the array while the function condition is true. Stops at the first fasle value.*

* Params:
    - 1: arr: Array
    - 2: Function(item)

Example:
```json5
ds.arrays.takeWhile([1,2,3,4,5],function(item) item <3)
```
Output:
```json5
[1,2]
```
---
## binaries
### fromBase64
*Converts a value from base64*

* Params:
    - 1: value: String

Example:
```json5
ds.binaries.fromBase64("SGVsbG8gV29ybGQ=")
```
Output:
```json5
"Hello World"
```
---
### fromHex
*Converts a value from hexadecimal*

* Params:
    - 1: value: String

Example:
```json5
ds.binaries.fromHex("48656C6C6F20576F726C64")
```
Output:
```json5
"Hello World"
```
---
### readLinesWith
*Reads the value with the specified encoding*

* Params:
    - 1: value: String
    - 2: encoding: String

Example:
```json5
ds.binaries.readLinesWith("Hello World","UTF-8")
```
Output:
```json5
["Hello World"]
```
---
### toBase64
*Converts a value to base 64.*

* Params:
    - 1: value: Any

Example:
```json5
ds.binaries.toBase64("Hello World")
```
Output:
```json5
"SGVsbG8gV29ybGQ="
```
---
### toHex
*Converts a value to hexadecimal*

* Params:
    - 1: value: Any

Example:
```json5
ds.binaries.toHex("Hello World")
```
Output:
```json5
"48656C6C6F20576F726C64"
```
---
### writeLinesWith
*Writes the value with the specified encoding*

* Params:
    - 1: value: String
    - 2: encoding: String

Example:
```json5
ds.binaries.writeLinesWith(["Hello World"],"UTF-8")
```
Output:
```json5
"Hello World\n"
```
---
## numbers
### fromBinary
*Converts the given value from binary to decimal*

* Params:
    - 1: value: Number

Example:
```json5
ds.numbers.toBinary(1100100)
```
Output:
```json5
"100"
```
---
### fromHex
*Converts the value from hex to decimal.*

* Params:
    - 1: value: Number

Example:
```json5
ds.numbers.fromHex(64)
```
Output:
```json5
100
```
---
### fromRadixNumber
*Converts the given value with the given base value to decimal. For instance `fromRadixNumber("1100100",2)` 
converts the number 1100100 with base 2 (binary) to the number 100 with base 10 (decimal)*

* Params:
    - 1: value: Number
    - 2: num: Number

Example:
```json5
ds.numbers.fromRadixNumber(1101000,2)
```
Output:
```json5
104
```
---
### toBinary
*Converts the given value from decimal to binary*

* Params:
    - 1: value: Number

Example:
```json5
ds.numbers.toBinary(100)
```
Output:
```json5
"1100100"
```
---
### toHex
*Converts the value from decimal to hex*

* Params:
    - 1: value: Number

Example:
```json5
ds.numbers.toHex(100)
```
Output:
```json5
"64"
```
---
### toRadixNumber
*Converts the given value to the base value given in num. For instance `toRadixNumber(100,2)` 
converts the number 100 with base 10 (decimal) to the number 100 with base 2 (binary)*

* Params:
    - 1: value: Number
    - 2: num: Number

Example:
```json5
ds.numbers.toRadixNumber(104, 2)
```
Output:
```json5
"1101000"
```
---
## objects
### divideBy
*Creates an array of objects where each nested object has the specified number of key-value pairs.*

* Params:
    - 1: obj: Object
    - 2: num: Number

Example:
```json5
ds.objects.divideBy({a:1,b:2,c:3},2)
```
Output:
```json5
[
  {
    "a": 1,
    "b": 2
  },
  {
    "c": 3
  }
]
```
---
### everyEntry
*Returns a boolean depending on if all key-value pairs pass the function.*

* Params:
    - 1: obj: Object
    - 2: Function(value,key)

Example:
```json5
ds.objects.everyEntry({a:1,b:2,c:1},function(value,key) value < 2)
```
Output:
```json5
false
```
---
### mergeWith
*Combines two objects together*

* Params:
    - 1: obj1: Object
    - 2: obj2: Object

Example:
```json5
ds.objects.mergeWith({a:1},{b:2})
```
Output:
```json5
{
  "a": 1,
  "b": 2
}
```
---
### someEntry
*Returns a boolean depending on if at least one key-value pair passes the function.*

* Params:
    - 1: obj: Object
    - 2: Function(value,key)

Example:
```json5
ds.objects.someEntry({a:1,b:2,c:1},function(value,key) value < 2)
```
Output:
```json5
true
```
---
### takeWhile
*Takes all key value pairs that result in true from the function. Stops on the first value that fails.*

* Params:
    - 1: obj: Object
    - 2: Function(value,key)

Example:
```json5
ds.objects.takeWhile({a:1,b:2,c:1},function(value,key) value < 2)
```
Output:
```json5
{"a": 1}
```
---
## strings
### appendIfMissing
*Appends str1 with str2 if st1 does not already end with the value*

* Params:
    - 1: str1: String
    - 2: str2: String 

Example:
```json5
{
    existing: ds.strings.appendIfMissing("World Hello","Hello"),
    missing: ds.strings.appendIfMissing("World ","Hello")
}
```
Output:
```json5
{
  "existing": "World Hello",
  "missing": "World Hello"
}
```
---
### camelize
*Converts a string to camlized case*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.camelize("Hello_world")
```
Output:
```json5
"helloWorld"
```
---
### capitalize
*Converts words in a string to its capitalized value.*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.capitalize("hello world")
```
Output:
```json5
"Hello World"
```
---
### charCode
*Converts a character to its char code*

* Params:
    - 1: str: String<Char>

Example:
```json5
ds.strings.charCode("*")
```
Output:
```json5
42
```
---
### charCodeAt
*Returns the char code at a specific location in the string.*

* Params:
    - 1: str: String
    - 2: num: Number

Example:
```json5
ds.strings.charCodeAt("*",0)
```
Output:
```json5
42
```
---
### dasherize
*Converts a string to kebab-case*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.dasherize("Hello WorldX")
```
Output:
```json5
"hello-wrodl-x"
```
---
### fromCharCode
*Converts a number to its string value*

* Params:
    - 1: num: Number

Example:
```json5
ds.strings.fromCharCode(42)
```
Output:
```json5
"*"
```
---
### isAlpha
*Returns a boolean which determines if the provided string only contains alpha characters.*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.isAlpha("dfgdgd")
```
Output:
```json5
true
```
---
### isAlphanumeric
*Returns a boolean which determines if the provided string only contains alpha numeric values*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.isAlphanumeric("ve534c5g35gb3")
```
Output:
```json5
true
```
---
### isLowerCase
*Returns a boolean which determines if the provided string is all lowercase*

* Params:
    - 1: value:
    - 2: value:

Example:
```json5
ds.strings.isLowerCase("hello")
```
Output:
```json5
true
```
---
### isNumeric
*Returns a boolean which determines if the provided string contains only numbers.*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.isNumeric("34634")
```
Output:
```json5
true
```
---
### isUpperCase
*Returns a boolean which determines if the provided string is all uppercase*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.isUpperCase("HELLO")
```
Output:
```json5
true
```
---
### isWhitespace
*Returns a boolean which determines if the provided string is empty*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.isWhitespace("      ")
```
Output:
```json5
true
```
---
### leftPad
*Pads the left side of a string with spaces if the string is below the offset length.*

* Params:
    - 1: str: String
    - 2: offset: Number

Example:
```json5
ds.strings.leftPad("Hello",10)
```
Output:
```json5
"     Hello"
```
---
### ordinalize
*Converts a number to its number-string format. i.e. 1 becomes 1st*

* Params:
    - 1: num: Number

Example:
```json5
ds.strings.ordinalize(1)
```
Output:
```json5
"1st"
```
---
### pluralize
*Converts the singular value of a word to its plural counterpart. May not work with all edge cases*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.pluralize("car")
```
Output:
```json5
"cars"
```
---
### prependIfMissing
*Prepends str1 with str2 if st1 does not already begin with the value*

* Params:
    - 1: str1: String
    - 2: str2: String 

Example:
```json5
{
    existing: ds.strings.prependIfMissing("Hello World","Hello"),
    missing: ds.strings.prependIfMissing(" World","Hello")
}
```
Output:
```json5
{
  "existing": "Hello World",
  "missing": "Hello World"
}
```
---
### repeat
*Repeats the given string num number of times*

* Params:
    - 1: str: String
    - 2: num: Number

Example:
```json5
ds.strings.repeat("Hello ",2)
```
Output:
```json5
"Hello Hello "
```
---
### rightPad
*Pads the right side of a string with spaces if the string is below the offset length.*

* Params:
    - 1: str: String
    - 2: offset: Number

Example:
```json5
ds.strings.rightPad("Hello",10)
```
Output:
```json5
"Hello     "
```
---
### singularize
*Converts a plural word to a singular word. May not work with all edge cases.*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.singularize("cars")
```
Output:
```json5
"car"
```
---
### substringAfter
*Gets the substring after the first occurance of the seperator*

* Params:
    - 1: str: String
    - 2: sep: String

Example:
```json5
ds.strings.substringAfter("!XHelloXWorldXAfter", "X")
```
Output:
```json5
"HelloXWorldXAfter"
```
---
### substringAfterLast
*Gets the substring after the final occurance of the seperator*

* Params:
    - 1: str: String
    - 2: sep: String

Example:
```json5
ds.strings.substringAfterLast("!XHelloXWorldXAfter", "X")
```
Output:
```json5
"After"
```
---
### substringBefore
*Gets the substring before the first occurance of the seperator*

* Params:
    - 1: str: String
    - 2: sep: String

Example:
```json5
ds.strings.substringBefore("!XHelloXWorldXAfter", "X")
```
Output:
```json5
"!"
```
---
### substringBeforeLast
*Gets the substring before the final occurance of the seperator*

* Params:
    - 1: str: String
    - 2: sep: String

Example:
```json5
ds.strings.substringBeforeLast("!XHelloXWorldXAfter", "X")
```
Output:
```json5
"!XHelloXWorld"
```
---
### underscore
*Converts a string to snake case.*

* Params:
    - 1: str: String

Example:
```json5
ds.strings.underscore("Hello WorldX")
```
Output:
```json5
"hello_world_x"
```
---
### unwrap
*removes the prepended and appended values to the string.*

* Params:
    - 1: str: String
    - 2: wrapper: String

Example:
```json5
{
    exists: ds.strings.unwrap("Hello World Hello","Hello"),
    partial: ds.strings.unwrap("Hello World ","Hello"),
    missing: ds.strings.unwrap(" World ","Hello")
}
```
Output:
```json5
{
  "exists": " World ",
  "partial": " World Hello",
  "missing": " World "
}
```
---
### withMaxSize
*Limits the string size to the number of characters.*

* Params:
    - 1: str: String
    - 2: num: Number

Example:
```json5
ds.strings.withMaxSize("Hello World",5)
```
Output:
```json5
"Hello"
```
---
### wrapIfMissing
*Prepends and appends the wrapper string to the given value if the value is not already wrapped. 
Will update only missing side if it already has one wrapper value at the beginning or end.*

* Params:
    - 1: str: String
    - 2: wrapper: String

Example:
```json5
{
    exists: ds.strings.wrapIfMissing("Hello World Hello","Hello"),
    partial: ds.strings.wrapIfMissing("Hello World ","Hello"),
    missing: ds.strings.wrapIfMissing(" World ","Hello")
}
```
Output:
```json5
{
  "exists": "Hello World Hello",
  "partial": "Hello World Hello",
  "missing": "Hello World Hello"
}
```
---
### wrapWith
*Prepends and appends the wrapper string to the given value.*

* Params:
    - 1: str: String
    - 2: wrapper: String

Example:
```json5
ds.strings.wrapWith(" World ","Hello")
```
Output:
```json5
"Hello World Hello"
```
---
## datetime
This library uses Java’s DateTimeFormatter library to format the date to a consistent value using ISO_OFFSET_DATE_TIME. If your datetime is not in this format, you can use the parse function to convert it. After you are finished executing your logic, you can use the format function to set the output format.
### atBeginningOfDay
*Returns the given datetime at midnight.*

* Params:
    - 1: datetime: String

Example:
```json5
ds.datetime.atBeginningOfDay("2020-12-31T23:19:35Z")
```
Output:
```json5
"2020-12-31T00:00:00Z"
```
---
### atBeginningOfHour
*Returns the given datetime with the minutes and seconds set to zero.*

* Params:
  - 1: datetime: String

Example:
```json5
ds.datetime.atBeginningOfHour("2020-12-31T23:19:35Z")
```
Output:
```json5
"2020-12-31T23:00:00Z"
```
---
### atBeginningOfMonth
*Returns the given datetime with the day set to first of the month and the time set to midnight.*

* Params:
  - 1: datetime: String

Example:
```json5
ds.datetime.atBeginningOfMonth("2020-12-31T23:19:35Z")
```
Output:
```json5
"2020-12-01T00:00:00Z"
```
---
### atBeginningOfWeek
*Returns the given datetime at the first of the current week and the time set to midnight.*

* Params:
  - 1: datetime: String

Example:
```json5
ds.datetime.atBeginningOfWeek("2020-12-31T23:19:35Z")
```
Output:
```json5
"2020-12-27T00:00:00Z"
```
---
### atBeginningOfYear
*Returns the given datetime at the first of the year.*

* Params:
  - 1: datetime: String

Example:
```json5
ds.datetime.atBeginningOfWeek("2020-12-31T23:19:35Z")
```
Output:
```json5
"2020-01-01T00:00:00Z"
```
---
### changeTimeZone
*Changes the date timezone, retaining the instant. This normally results in a change to the local date-time.*

* Params:
  - 1: datetime: String
  - 2: timezone: String

Example:
```json5
ds.datetime.changeTimeZone("2020-12-31T23:19:35Z", "America/Los_Angeles")
```
Output:
```json5
"2020-12-31T15:19:35-08:00"
```
---
### compare
*Returns 1 if datetime1 > datetime2, -1 if datetime1 < datetime2, 0 if datetime1 == datetime2.*

* Params:
  - 1: datetime1: String
  - 2: datetime2: String

Example:
```json5
ds.datetime.compare("2020-12-31T23:19:35Z","2020-01-01T00:00:00Z")
```
Output:
```json5
1
```
---
### date
*This function uses a datetime object to generate a datetime in string format. Every key in the object is an optional number value, except the timezone which is an optional string.*

* Params:
  - 1: datetime: Object

* DateTime Object Structure:
```json5
{
    "year": 0,
    "month": 0,
    "day": 0,
    "hour": 0,
    "minute": 0,
    "second": 0,
    "timezone": "Z"
}
```

Example:
```json5
local datetime={
  "year": 2021,
  "timezone": "America/Los_Angeles"
};
ds.datetime.date(datetime)
```
Output:
```json5
"2021-01-01T00:00:00-08:00"
```
---
### daysBetween
*Returns the number of days between `datetime1` and `datetime2.`*

* Params:
  - 1: datetime1: String
  - 2: datetime2: String

Example:
```json5
local date1 = "2019-09-20T18:53:41.425Z";
local date2 = "2019-09-14T18:53:41.425Z";

ds.datetime.daysBetween(date1, date2)
```
Output:
```json5
6
```
---
### format
*Given a datetime, will convert it to the specified output format.*

* Params:
  - 1: datetime: String
  - 2: outputFormat: String

Example:
```json5
ds.datetime.format("2019-09-20T18:53:41.425Z", "yyyy/MM/dd")
```
Output:
```json5
"2019/09/20"
```
---
### isLeapYear
*Returns a boolean indicating if `datetime` is a leap year.*

* Params:
  - 1: datetime: String

Example:
```json5
ds.datetime.isLeapYear("2019-09-14T18:53:41.425Z")
```
Output:
```json5
false
```
---
### minus
*Subtracts a `period` type from the given datetime.*

* Params:
  - 1: datetime: String
  - 2: period: String

Example:
```json5
ds.datetime.minus("2019-09-20T18:53:41Z", "P2D")
```
Output:
```json5
"2019-09-18T18:53:41Z"
```
---
### now
*Returns current datetime.*

Example:
```json5
ds.datetime.now()
```
Output:
```json5
"2021-01-05T13:09:45.476375-05:00"
```
---
### parse
*Parses the datetime using the input format and returns the value in the default format. If an epoch or timestamp value is used as the datetime you can use `"epoch"` or `"timestamp"` as the inputFormat*

* Params:
  - 1: datetime: String|Number
  - 2: inputFormat: String

Example:
```json5
ds.datetime.parse("12/31/1990 10:10:10", "MM/dd/yyyy HH:mm:ss")
```
Output:
```json5
"1990-12-31T10:10:10Z"
```
---
### plus
*Adds a `period` type from the given datetime.*

* Params:
  - 1: datetime: String
  - 2: period: String

Example:
```json5
ds.datetime.plus("2019-09-18T18:53:41Z", "P2D")
```
Output:
```json5
"2019-09-20T18:53:41Z"
```
---
### toLocalDate
*Converts a zone datetime to a local date*

* Params:
  - 1: datetime: String

Example:
```json5
ds.datetime.toLocalDate("2019-07-04T18:53:41Z")
```
Output:
```json5
"2019-07-04"
```
---
### toLocalDateTime
*Converts a zone datetime to a local datetime.*

* Params:
    - 1: datetime: String

Example:
```json5
ds.datetime.toLocalDateTime("2019-07-04T21:00:00Z")
```
Output:
```json5
"2019-07-04T21:00:00"
```
---
### toLocalTime
*Converts a zone datetime to a local time.*

* Params:
    - 1: datetime: String

Example:
```json5
ds.datetime.toLocalTime("2019-07-04T21:00:00Z")
```
Output:
```json5
"21:00:00"
```
---
### today
*Returns the datetime of the current day at midnight.*

Example:
```json5
ds.datetime.today
```
Output:
```json5
"2021-01-05T00:00:00-05:00"
```
---
### tomorrow
*Returns the datetime of the next day at midnight.*

Example:
```json5
ds.datetime.tomorrow
```
Output:
```json5
"2021-01-06T00:00:00-05:00"
```
---
### yesterday
*Returns the datetime of the previous day at midnight.*

Example:
```json5
ds.datetime.yesterday
```
Output:
```json5
"2021-01-04T00:00:00-05:00"
```
---
## period
### between
*Returns the period between two datetime objects.*

* Params:
  - 1: datetime1: String
  - 2: datetime2: String

Example:
```json5
ds.period.between(
  ds.datetime.date({year:2020}),
  ds.datetime.date({year:2019, month: 3})
)
```
Output:
```json5
"P-10M"
```
---
### days
*Returns the number of given days in period format.*

* Params:
  - 1: num: Number

Example:
```json5
ds.period.days(5)
```
Output:
```json5
"P5D"
```
---
### duration
*Returns the given time object in a Period of Time format*

* Params:
  - 1: time: Object
  
* Time Object Structure:
```json5
{
  days: 0,
  hours: 0,
  minutes: 0,
  seconds: 0
}
```

Example:
```json5
ds.period.days(5)
```
Output:
```json5
"PT25H1M1S"
```
---
### hours
*Returns the number of given hours in a Period of Time format.*

* Params:
  - 1: num: Number

Example:
```json5
ds.period.hours(1)
```
Output:
```json5
"PT1H"
```
---
### minutes
*Returns the number of given minutes in a Period of Time format.*

* Params:
  - 1: num: Number

Example:
```json5
ds.period.minutes(1)
```
Output:
```json5
"PT1M"
```
---
### months
*Returns the number of given months in a Period format*

* Params:
  - 1: num: Number

Example:
```json5
ds.period.months(1)
```
Output:
```json5
"P1M"
```
---
### period
*Returns the given time object in a Period format*

* Params:
  - 1: period: Object

* Example Period Structure
```json5
{
  years: 0,
  months: 0,
  days: 0
}
```
Example:
```json5
ds.period.period({years: 1, months: 1, days: 1})
```
Output:
```json5
"P1Y1M1D"
```
---
### seconds
*Returns the number of given seconds in a Period of Time format.*

* Params:
  - 1: num: Number

Example:
```json5
ds.period.seconds(1)
```
Output:
```json5
"PT1S"
```
---
### years
*Returns the number of given years in a Period format*

* Params:
  - 1: num: Number

Example:
```json5
ds.period.years(1)
```
Output:
```json5
"P1Y"
```
---