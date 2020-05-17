# std

Standard Library
This page describes the functions available in Jsonnet's standard library, i.e. the object implicitly bound to the std variable. Some of the standard library functions can be implemented in Jsonnet. Their code can be found in the std.jsonnet file. The behavior of some of the other functions, i.e. the ones that expose extra functionality not otherwise available to programmers, is described formally in the specification.

The standard library is implicitly added to all Jsonnet programs by enclosing them in a local construct. For example, if the program given by the user is {x: "foo"}, then the actual code executed would be local std = { ... }; {x: "foo"}. The functions in the standard library are all hidden fields of the std object.

---
## External Variables
### `std.extVar(x)`
If an external variable with the given name was defined, return its string value. Otherwise, raise an error.

---
## Types and Reflection


### `std.thisFile():string`
Note that this is a field. It contains the current Jsonnet filename as a string.

Example:
```scala 3
std.thisFile
```
Output:
```json5
"example.ds"
```

---
### `std.type(x:any):string`
Return a string that indicates the type of the value. The possible return values are: "array", "boolean", "function", "null", "number", "object", and "string".

The following functions are also available and return a boolean: std.isArray(v), std.isBoolean(v), std.isFunction(v), std.isNumber(v), std.isObject(v), and std.isString(v).

Example:
```json5
{
    array: std.type([]),
    boolean: std.type(true),
    "function": std.type(function(x) x),
    null: std.type(null),
    number: std.type(0),
    object: std.type({}),
    string: std.type("")
}
```
Output:
```json
{
  "array": "array",
  "boolean": "boolean",
  "function": "function",
  "null": "null",
  "number": "number",
  "object": "object",
  "string": "string"
}
```

---
### `std.length(x):number`
Depending on the type of the value given, either returns the number of elements in the array, the number of codepoints in the string, the number of parameters in the function, or the number of fields in the object. Raises an error if given a primitive value, i.e. null, true or false.

Example:
```json5
{
    array: std.length([5,3,2]),
    string: std.length("Hello World"),
    "function": std.length(function(x,y)x),
    object: std.length({a:1,b:2})
}
```
Output:
```json
{
  "array": 3,
  "function": 2,
  "object": 2,
  "string": 11
}
```
---
### `std.objectHas(o:object, f:string):boolean`
Returns true if the given object has the field (given as a string), otherwise false. Raises an error if the arguments are not object and string respectively. Returns false if the field is hidden.

Example:
```json5
local obj={
    message: "Hello World"
};

{
    "message": std.objectHas(obj, "message"),
    "value": std.objectHas(obj, "value")
}
```
Output:
```json5
{
  "message": true,
  "value": false
}
```

---
### `std.objectFields(o:object):array`
Returns an array of strings, each element being a field from the given object. Does not include hidden fields.

Example:
```json5
local obj={
    message: "Hello World",
    value:: "hidden field"
};

std.objectFields(obj)
```
Output:
```json5
[
  "message"
]
```

---
### `std.objectHasAll(o:object, f:string):boolean`
As std.objectHas but also includes hidden fields.

Example:
```json5
local obj={
    message:: "Hello World"
};

{
    "hiddenCheck": std.objectHasAll(obj, "message"),
    "normalCheck": std.objectHas(obj, "message")
    
}
```
Output:
```json5
{
  "hiddenCheck": true,
  "normalCheck": false
}
```

---
### `std.objectFieldsAll(o:object):array`
As std.objectFields but also includes hidden fields.

Example:
```json5
local obj={
    message: "Hello World",
    value:: "hidden field"
};

std.objectFieldsAll(obj)
```
Output:
```json5
[
  "message",
  "value"
]
```

---
### `std.prune(a:array):array`
Recursively remove all "empty" members of a. "Empty" is defined as zero length `arrays`, zero length `objects`, or `null` values. The argument a may have any type.

Example:
```json5
std.prune([1,[],2,{},3,null])
```
Output:
```json5
[
  1,
  2,
  3
]
```

---
### `std.mapWithKey(func:function, obj:object):object`
Apply the given function to all fields of the given object, also passing the field name. The function func is expected to take the field name as the first parameter and the field value as the second.

Example:
```json5
local obj={
    "message": "Hello World"  ,
    "test": "value"
};

std.mapWithKey(function(key,vvalue) key == "message", obj)
```
Output:
```json5
{
  "message": true,
  "test": false
}
```

---
## Mathematical Utilities
The following mathematical functions are available:

---
### `std.abs(n:number):number`

Example:
```json5
std.abs(-1)
```
Output:
```json5
1
```

---
### `std.sign(n)`

Example:
```json5
std.sign(1) // error
```
Output:
```json5

```

---
### `std.max(a:number, b:number):number`

Example:
```json5
std.max(5,1)
```
Output:
```json5
5
```

---
### `std.min(a:number, b:number):number`

Example:
```json5
std.min(5,1)
```
Output:
```json5
1
```

---
### `std.pow(x:number, exp:number):number`

Example:
```json5
std.pow(5,2)
```
Output:
```json5
25
```

---
### `std.exp(x:number):number`
The std.exp(x) function returns e<sup>x</sup>, where x is the argument, and e is Euler's number

Example:
```json5
std.exp(-1)
```
Output:
```json5
0.36787944117144233
```

---
### `std.log(x:number):number`

Example:
```json5
std.log(2)
```
Output:
```json5
0.6931471805599453
```

---
### `std.exponent(x:number):number`

Example:
```json5
std.exponent(10)
```
Output:
```json5
4
```

---
### `std.mantissa(x:number):number`

Example:
```json5
std.mantissa(10)
```
Output:
```json5
0.625
```

---
### `std.floor(x:number):number`

Example:
```json5
std.floor(1.9)
```
Output:
```json5
1
```

---
### `std.ceil(x:number):number`

Example:
```json5
std.ceil(1.9)
```
Output:
```json5
2
```

---
### `std.sqrt(x:number):number`

Example:
```json5
std.sqrt(4)
```
Output:
```json5
2
```

---
### `std.sin(x:number):number`

Example:
```json5
std.sin(1)
```
Output:
```json5
0.8414709848078965
```

---
### `std.cos(x:number):number`

Example:
```json5
std.cos(1)
```
Output:
```json5
0.5403023058681398
```

---
### `std.tan(x:number):number`

Example:
```json5
std.tan(1)
```
Output:
```json5
1.5574077246549023
```

---
### `std.asin(x:number):number`

Example:
```json5
std.asin(1)
```
Output:
```json5
1.5707963267948966
```

---
### `std.acos(x:number):number`

Example:
```json5
std.acos(1)
```
Output:
```json5
0
```

---
### `std.atan(x:number):number`
The function std.mod(a, b) is what the % operator is desugared to. It performs modulo arithmetic if the left hand side is a number, or if the left hand side is a string, it does Python-style string formatting with std.format().

Example:
```json5
std.atan(1)
```
Output:
```json5
0.7853981633974483
```

---
### `std.clamp(x, minVal, maxVal)`
Available since version 0.15.0.
Clamp a value to fit within the range [minVal, maxVal]. Equivalent to std.min(minVal, std.max(x, maxVal)).

Example: 
```json5
std.clamp(-3, 0, 5) //error
```
Output:
```json5
0
```

---
## Assertions and Debugging
### `std.assertEqual(any, any):boolean`
Ensure that a == b. Returns true or throws an error message.

Example:
```json5
std.assertEqual(1, 2)
```
Output:
```json5
Problem executing map: sjsonnet.Error: assertEqual failed: 1 != 2
    at line 6 column 16 of the transformation
```

---
## String Manipulation
### `std.toString(any):string`
Convert the given argument to a string.

Example:
```json5
{
    "null": std.toString(null),
    "number": std.toString(0),
    "boolean": std.toString(true),
    "object": std.toString({hello: "world"}),
    "array": std.toString([1,2,3])
}
```
Output:
```json5
{
  "array": "[1, 2, 3]",
  "boolean": "true",
  "null": "null",
  "number": "0",
  "object": "{\"hello\": \"world\"}"
}
```

---
### `std.codepoint(str)`
Returns the positive integer representing the unicode codepoint of the character in the given single-character string. This function is the inverse of std.char(n).

---
### `std.char(n)`
Returns a string of length one whose only unicode codepoint has integer id n. This function is the inverse of std.codepoint(str).

---
### `std.substr(str, from, len)`
Returns a string that is the part of s that starts at offset from and is len codepoints long. If the string s is shorter than from+len, the suffix starting at position from will be returned.

---
### `std.findSubstr(pat, str)`
Returns an array that contains the indexes of all occurances of pat in str.

---
### `std.startsWith(a, b)`
Returns whether the string a is prefixed by the string b.

---
### `std.endsWith(a, b)`
Returns whether the string a is suffixed by the string b.

---
### `std.stripChars(str, chars)`
Available since version 0.15.0.
Removes characters chars from the beginning and from the end of str.

Example: std.stripChars(" test test test ", " ") yields "test test test".

Example: std.stripChars("aaabbbbcccc", "ac") yields "bbbb".

Example: std.stripChars("cacabbbbaacc", "ac") yields "bbbb".

---
### `std.lstripChars(str, chars)`
Available since version 0.15.0.
Removes characters chars from the beginning of str.

Example: std.lstripChars(" test test test ", " ") yields "test test test ".

Example: std.lstripChars("aaabbbbcccc", "ac") yields "bbbbcccc".

Example: std.lstripChars("cacabbbbaacc", "ac") yields "bbbbaacc".

---
### `std.rstripChars(str, chars)`
Available since version 0.15.0.
Removes characters chars from the beginning and from the end of str.

Example: std.rstripChars(" test test test ", " ") yields " test test test".

Example: std.rstripChars("aaabbbbcccc", "ac") yields "aaabbbb".

Example: std.rstripChars("cacabbbbaacc", "ac") yields "cacabbbb".

---
### `std.split(str, c)`
Split the string str into an array of strings, divided by the single character c.

Example: std.split("foo/bar", "/") yields ["foo", "bar"].

Example: std.split("/foo/", "/") yields ["", "foo", ""].

---
### `std.splitLimit(str, c, maxsplits)`
As std.split(str, c) but will stop after maxsplits splits, thereby the largest array it will return has length maxsplits + 1. A limit of -1 means unlimited.

Example: std.splitLimit("foo/bar", "/", 1) yields ["foo", "bar"].

Example: std.splitLimit("/foo/", "/", 1) yields ["", "foo/"].

---
### `std.strReplace(str, from, to)`
Returns a copy of the string in which all occurrences of string from have been replaced with string to

Example: std.strReplace('I like to skate with my skateboard', 'skate', 'surf') yields "I like to surf with my surfboard".

---
### `std.asciiUpper(str)`
Returns a copy of the string in which all ASCII letters are capitalized.

Example: std.asciiUpper('100 Cats!') yields "100 CATS!".

---
### `std.asciiLower(str)`
Returns a copy of the string in which all ASCII letters are lower cased.

Example: std.asciiLower('100 Cats!') yields "100 cats!".

---
### `std.stringChars(str)`
Split the string str into an array of strings, each containing a single codepoint.

Example: std.stringChars("foo") yields ["f", "o", "o"].

---
### `std.format(str, vals)`
Format the string str using the values in vals. The values can be an array, an object, or in other cases are treated as if they were provided in a singleton array. The string formatting follows the same rules as Python. The % operator can be used as a shorthand for this function.

Example: std.format("Hello %03d", 12) yields "Hello 012".

Example: "Hello %03d" % 12 yields "Hello 012".

Example: "Hello %s, age %d" % ["Foo", 25] yields "Hello Foo, age 25".

Example: "Hello %(name)s, age %(age)d" % {age: 25, name: "Foo"} yields "Hello Foo, age 25".

---
### `std.escapeStringBash(str)`
Wrap str in single quotes, and escape any single quotes within str by changing them to a sequence '"'"'. This allows injection of arbitrary strings as arguments of commands in bash scripts.

---
### `std.escapeStringDollars(str)`
Convert $ to $$ in str. This allows injection of arbitrary strings into systems that use $ for string interpolation (like Terraform).

---
### `std.escapeStringJson(str)`
Convert str to allow it to be embedded in a JSON representation, within a string. This adds quotes, escapes backslashes, and escapes unprintable characters.

Example:
{ local description = "Multiline\nc:\\path", json: "{name: %s}" % std.escapeStringJson(description) }
yields: {"json": "{name: \"Multiline\\nc:\\\\path\"}"}

---
### `std.escapeStringPython(str)`
Convert str to allow it to be embedded in Python. This is an alias for std.escapeStringJson.

---
## Parsing
### `std.parseInt(str)`
Parses a signed decimal integer from the input string.

Example: std.parseInt("123") yields 123.

Example: std.parseInt("-123") yields -123.

---
### `std.parseOctal(str)`
Parses an unsigned octal integer from the input string. Initial zeroes are tolerated.

Example: 
```json5
std.parseOctal("755") 
```
```json5
493
```

---
### `std.parseHex(str)`
Parses an unsigned hexadecimal integer, from the input string. Case insensitive.

Example: 
```json5
std.parseHex("ff")
```
```json5
255
```

---
### `std.parseJson(str)`
Available since version 0.13.0.
Parses a JSON string.

Example: 
```json5
std.parseJson('{"foo": "bar"}')
```
Output:
```json5
{
  "foo": "bar"
}
```
---
### `std.encodeUTF8(str)`
Available since version 0.13.0.
Encode a string using UTF8. Returns an array of numbers representing bytes.

---
### `std.decodeUTF8(arr)`
Available since version 0.13.0.
Decode an array of numbers representing bytes using UTF8. Returns a string.

---
## Manifestation
### `std.manifestIni(ini)`
Convert the given structure to a string in INI format. This allows using Jsonnet's object model to build a configuration to be consumed by an application expecting an INI file. The data is in the form of a set of sections, each containing a key/value mapping. These examples should make it clear:

Example:
```json5
{
    main: { a: "1", b: "2" },
    sections: {
        s1: {x: "11", y: "22", z: "33"},
        s2: {p: "yes", q: ""},
        empty: {},
    }
}
```
Output:
```
a = 1
b = 2
[empty]
[s1]
x = 11
y = 22
z = 33
[s2]
p = yes
q = 
```
---
### `std.manifestPython(v)`
Convert the given value to a JSON-like form that is compatible with Python. The chief differences are True / False / None instead of true / false / null.

Example:
```json5{
  b: ["foo", "bar"],
  c: true,
  d: null,
  e: { f1: false, f2: 42 },
}
```
Output:
```json5
{
  "b": ["foo", "bar"],
  "c": True,
  "d": None,
  "e": {"f1": False, "f2": 42}
}
```
---
### `std.manifestPythonVars(conf)`
Convert the given object to a JSON-like form that is compatible with Python. The key difference to std.manifestPython is that the top level is represented as a list of Python global variables.

{
  b: ["foo", "bar"],
  c: true,
  d: null,
  e: { f1: false, f2: 42 },
}
Yields a string containing this Python code:

b = ["foo", "bar"]
c = True
d = None
e = {"f1": False, "f2": 42}

---
### `std.manifestJsonEx(value, indent)`
Convert the given object to a JSON form. indent is a string containing one or more whitespaces that are used for indentation:

std.manifestJsonEx(
{
    x: [1, 2, 3, true, false, null,
        "string\nstring"],
    y: { a: 1, b: 2, c: [1, 2] },
}, "    ")
Yields a string containing this JSON object:

{
    "x": [
        1,
        2,
        3,
        true,
        false,
        null,
        "string\nstring"
    ],
    "y": {
        "a": 1,
        "b": 2,
        "c": [
            1,
            2
        ]
    }
}

---
### `std.manifestYamlDoc(value)`
Convert the given value to a YAML form. Note that std.manifestJson could also be used for this purpose, because any JSON is also valid YAML. But this function will produce more canonical-looking YAML.

std.manifestYamlDoc(
  {
    x: [1, 2, 3, true, false, null,
        "string\nstring\n"],
    y: { a: 1, b: 2, c: [1, 2] },
  },
  indent_array_in_object=false)
Yields a string containing this YAML:

"x":
  - 1
  - 2
  - 3
  - true
  - false
  - null
  - |
    string
    string
"y":
  "a": 1
  "b": 2
  "c":
    - 1
    - 2
The indent_array_in_object param adds additional indentation which some people may find easier to read.

---
### `std.manifestYamlStream(value)`
Given an array of values, emit a YAML "stream", which is a sequence of documents separated by --- and ending with ....

std.manifestYamlStream(
  ['a', 1, []],
  indent_array_in_object=false,
  c_document_end=true)
Yields this string:

---
"a"
---
1
---
[]
...
The indent_array_in_object param is the same as in manifestYamlDoc.

The c_document_end param adds the optional terminating ....

---
### `std.manifestXmlJsonml(value)`
Convert the given JsonML-encoded value to a string containing the XML.

std.manifestXmlJsonml([
  'svg', { height: 100, width: 100 },
  [
    'circle', {
      cx: 50, cy: 50, r: 40,
       stroke: 'black', 'stroke-width': 3,
       fill: 'red',
    }
  ],
])
Yields a string containing this XML (all on one line):

<svg height="100" width="100">
  <circle cx="50" cy="50" fill="red" r="40"
  stroke="black" stroke-width="3"></circle>
</svg>
Which represents the following image:

JsonML is designed to preserve "mixed-mode content" (i.e., textual data outside of or next to elements). This includes the whitespace needed to avoid having all the XML on one line, which is meaningful in XML. In order to have whitespace in the XML output, it must be present in the JsonML input:

std.manifestXmlJsonml([
  'svg',
  { height: 100, width: 100 },
  '\n  ',
  [
    'circle',
    {
      cx: 50, cy: 50, r: 40, stroke: 'black',
      'stroke-width': 3, fill: 'red',
    }
  ],
  '\n',
])

---
## Arrays
### `std.makeArray(sz, func)`
Create a new array of sz elements by calling func(i) to initialize each element. Func is expected to be a function that takes a single parameter, the index of the element it should initialize.

Example: std.makeArray(3,function(x) x * x) yields [0, 1, 4].

---
### `std.member(arr, x)`
Available since version 0.15.0.
Returns whether x occurs in arr. Argument arr may be an array or a string.

---
### `std.count(arr, x)`
Return the number of times that x occurs in arr.

---
### `std.find(value, arr)`
Returns an array that contains the indexes of all occurances of value in arr.

---
### `std.map(func, arr)`
Apply the given function to every element of the array to form a new array.

---
### `std.mapWithIndex(func, arr)`
Similar to map above, but it also passes to the function the element's index in the array. The function func is expected to take the index as the first parameter and the element as the second.

---
### `std.filterMap(filter_func, map_func, arr)`
This is primarily used to desugar array comprehension syntax. It first filters, then maps the given array, using the two functions provided.

---
### `std.flatMap(func, arr)`
Apply the given function to every element of the array to form a new array then flatten the result. It can be thought of as a generalized map, where each element can get mapped to 0, 1 or more elements.

Example: std.flatMap(function(x) [x, x], [1, 2, 3]) yields [1, 1, 2, 2, 3, 3]).

Example: std.flatMap(function(x) if x == 2 then [] else [x], [1, 2, 3]) yields [1, 3]).

Example: std.flatMap(function(x) if x == 2 then [] else [x * 3, x * 2], [1, 2, 3]) yields [3, 2, 9, 6]).

---
### `std.filter(func, arr)`
Return a new array containing all the elements of arr for which the func function returns true.

---
### `std.foldl(func, arr, init)`
Classic foldl function. Calls the function on the result of the previous function call and each array element, or init in the case of the initial element. Traverses the array from left to right.

---
### `std.foldr(func, arr, init)`
Classic foldl function. Calls the function on each array element and the result of the previous function call, or init in the case of the initial element. Traverses the array from right to left.

---
### `std.range(from, to)`
Return an array of ascending numbers between the two limits, inclusively.

---
### `std.repeat(what, count)`
Available since version 0.15.0.
Repeats an array or a string what a number of times specified by an integer count.

Example: std.repeat([1, 2, 3], 3) yields [1, 2, 3, 1, 2, 3, 1, 2, 3].

Example 2: std.repeat("blah", 2) yields "blahblah".

---
### `std.join(sep, arr)`
If sep is a string, then arr must be an array of strings, in which case they are concatenated with sep used as a delimiter. If sep is an array, then arr must be an array of arrays, in which case the arrays are concatenated in the same way, to produce a single array.

Example1: std.join(".", ["www", "google", "com"]) yields "www.google.com".

Example2: std.join([9, 9], [[1], [2, 3]]) yields [1, 9, 9, 2, 3].

---
### `std.lines(arr)`
Concatenate an array of strings into a text file with newline characters after each string. This is suitable for constructing bash scripts and the like.

---
### `std.flattenArrays(arrs)`
Concatenate an array of arrays into a single array.

Example: std.flattenArrays([[1, 2], [3, 4], [[5, 6], [7, 8]]]) yields [1, 2, 3, 4, [5, 6], [7, 8]].

---
### `std.reverse(arr)`
Available since version 0.13.0.
Reverses an array.

---
### `std.sort(arr, keyF=id)`
Sorts the array using the <= operator.

Optional argument keyF is a single argument function used to extract comparison key from each array element. Default value is identity function keyF=function(x) x.

---
### `std.uniq(arr, keyF=id)`
Removes successive duplicates. When given a sorted array, removes all duplicates.

Optional argument keyF is a single argument function used to extract comparison key from each array element. Default value is identity function keyF=function(x) x.

---
## Sets
Sets are represented as ordered arrays without duplicates.

Note that the std.set* functions rely on the uniqueness and ordering on arrays passed to them to work. This can be guarenteed by using std.set(arr). If that is not the case, the functions will quietly return non-meaningful results.

All set.set* functions accept keyF function of one argument, which can be used to extract key to use from each element. All Set operations then use extracted key for the purpose of identifying uniqueness. Default value is identity function local id = function(x) x

---
### `std.set(arr, keyF=id)`
Shortcut for std.uniq(std.sort(arr)).

---
### `std.setInter(a, b, keyF=id)`
Set intersection operation (values in both a and b).

---
### `std.setUnion(a, b, keyF=id)`
Set union operation (values in any of a or b). Note that + on sets will simply concatenate the arrays, possibly forming an array that is not a set (due to not being ordered without duplicates).

Example1: std.setUnion([1, 2], [2, 3]) yields [1, 2, 3].

Example2: std.setUnion([{n:"A", v:1}, {n:"B"}], [{n:"A", v: 9999}, {n:"C"}],keyF=function(x) x.n) yields [{n: "A", v:1}, {n: "B"}, {n: "C"}].

---
### `std.setDiff(a, b, keyF=id)`
Set difference operation (values in a but not b).

---
### `std.setMember(x, arr, keyF=id)`
Returns true if x is a member of array, otherwise false.

---
## Encoding
### `std.base64(input)`
Encodes the given value into a base64 string. The encoding sequence is A-Za-z0-9+/ with = to pad the output to a multiple of 4 characters. The value can be a string or an array of numbers, but the codepoints / numbers must be in the 0 to 255 range. The resulting string has no line breaks.

---
### `std.base64DecodeBytes(str)`
Decodes the given base64 string into an array of bytes (number values). Currently assumes the input string has no linebreaks and is padded to a multiple of 4 (with the = character). In other words, it consumes the output of std.base64().

---
### `std.base64Decode(str)`
Deprecated, use std.base64DecodeBytes and decode the string explicitly (e.g. with std.decodeUTF8) instead.
Behaves like std.base64DecodeBytes() except returns a naively encoded string instead of an array of bytes.

---
### `std.md5(s)`
Encodes the given value into an MD5 string.

---
## JSON Merge Patch
### `std.mergePatch(target, patch)`
Applies patch to target according to RFC7396

---
## Debugging
### `std.trace(str, rest)`
Available since version 0.11.0.
Outputs the given string str to stderr and returns rest as the result.

Example:
```scala 3
local conditionalReturn(cond, in1, in2) =
    if (cond) then
        std.trace('cond is true returning '
                  + std.toString(in1), in1)
    else
        std.trace('cond is false returning '
                  + std.toString(in2), in2);


{
    a: conditionalReturn(true, { b: true }, { c: false }),
} 
```
Outout:
```json5
//TRACE: test.jsonnet:3 cond is true returning {"b": true}
{
   "a": {
      "b": true
   }
}
```
---
## DS
## Crypto
### `hash(value:string, algorithm:string):string`
Calculates hash of a String value using one of the supported algorithms. The `algorithm` must be one of `MD2`, `MD5`, `SHA-1`, `SHA-256`, `SHA-384`, `SHA-512`
The response is a string containing the hash bytes.

Example:
```json5
DS.Crypto.hash("string", "SHA-512")
```
Output:
```json5
"2757cb3cafc39af451abb2697be79b4ab61d63d74d85b0418629de8c26811b529f3f3780d0150063ff55a2beee74c4ec102a2a2731a1f1f7f10d473ad18a6a87"
```
---
### `hmac(value:string, secret:string, algorithm:string):string`
Generates hash-based message autentication code using provided secret and a hash function algorithm. The `algoritm` must be one of `HmacSHA1`, `HmacSHA256` or `HmacSHA512`.

Example:
```json5
{
    hmacValue: DS.Crypto.hmac("HelloWorld", "DataSonnet rules!", "HmacSHA256")
}
```
Output:
```json5
{
    "hmacValue": "14357838ce44c7c9578bb9366994e8683cf69a52de4930b72e1802ed55392caf"
}
```
---
### `encrypt(value:string, key:string):string`
Encrypts the string using the key and the Blowfish algorithm. The result is Base64-encoded encrypted string.

Example:
```json5
{
    passwd: DS.Crypto.encrypt("HelloWorld", "DataSonnet123")
}
```
Output:
```json5
{
    "passwd": "HdK8opktKiK3ero0RJiYbA=="
}
```
---
### `decrypt(value:string, key:string):string`
Decrypts the string using the key and the Blowfish algorithm.

Example:
```json5
{
    passwd: DS.Crypto.decrypt("HdK8opktKiK3ero0RJiYbA==", "DataSonnet123")
}
```
Output:
```json5
{
    "passwd": "HelloWorld"
}
```
---
## ZonedDateTime
### `now():string`
Returns the current date/time from the system UTC clock in ISO-8601 format.

Example:
```json5
{
    currentZuluTime: DS.ZonedDateTime.now()
}
```
Output:
```json5
{
    "currentZuluTime": "2019-08-19T18:58:38.313Z"
}
```
---
### `offset(datetime:string, period:string):string`
Returns a copy of this datetime with the specified amount added. The `datetime` parameter is in the ISO-8601 format.
The `period` is a string in the ISO-8601 period format.

Example:
```json5
DS.ZonedDateTime.offset("2019-07-22T21:00:00Z", "P1Y1D")
```
Output:
```json5
"2020-07-23T21:00:00Z"
```
---
### `format(datetime:string, inputFormat:string, outputFormat:string):string`
Reformats a zoned date-time string.

Example:
```json5
DS.ZonedDateTime.format("2019-07-04T21:00:00Z", "yyyy-MM-dd'T'HH:mm:ssVV", "d MMM uuuu")
```
Output:
```json5
"4 Jul 2019"
```
---
### `compare(datetime1:string, format1:string, datetime2:string, format2:string):string`
Returns 1 if datetime1 > datetime2, -1 if datetime1 < datetime2, 0 if datetime1 == datetime2.

Example:
```json5

```
Output:
```json5

```
---
### `changeTimeZone(datetime:string, format:string, timezone:string):string`
Changes the date timezone, retaining the instant. This normally results in a change to the local date-time.
The response is formatted using the same format as an input.

Example:
```json5
DS.ZonedDateTime.changeTimeZone("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ", "America/Los_Angeles")
```
Output:
```json5
"2019-07-04T19:00:00-0700"
```
---
### `toLocalDate(datetime:string, format:string):string`
Returns only local date part of the `datetime` parameter in the ISO-8601 format without the offset.

Example:
```json5
DS.ZonedDateTime.toLocalDate("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ")
```
Output:
```json5
"2019-07-04"
```
---
### `toLocalTime(datetime:string, format:string):string`
Returns only local time part of the `datetime` parameter in the ISO-8601 format without the offset.

Example:
```json5
DS.ZonedDateTime.toLocalTime("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ")
```
Output:
```json5
"21:00:00"
```
---
### `toLocalDateTime(datetime:string, format:string):string`
Returns local datetime part of the `datetime` parameter in the ISO-8601 format without the offset.

Example:
```json5
DS.ZonedDateTime.toLocalDateTime("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ")
```
Output:
```json5
"2019-07-04T21:00:00"
```
---
## LocalDateTime
### `now():string`
Returns the current date/time from the system UTC clock in ISO-8601 format without a time zone.

Example:
```json5
{
    currentLocalTime: DS.LocalDateTime.now()
}
```
Output:
```json5
{
    "currentLocalTime": "2019-08-19T18:58:38.313"
}
```
---
### `offset(datetime:string, period:string):string`
Returns a copy of this datetime with the specified amount added. The `datetime` parameter is in the ISO-8601 format without an offset.
The `period` is a string in the ISO-8601 period format.

Example:
```json5
DS.LocalDateTime.offset("2019-07-22T21:00:00", "P1Y1D")
```
Output:
```json5
"2020-07-23T21:00:00"
```
---
### `format(datetime:string, inputFormat:string, outputFormat:string):string`
Reformats a local date-time string.

Example:
```json5
DS.LocalDateTime.format("2019-07-04T21:00:00", "yyyy-MM-dd'T'HH:mm:ss", "d MMM uuuu")
```
Output:
```json5
"4 Jul 2019"
```
---
### `compare(string datetime1, string format1, string datetime2, string format2):number`
Returns `1` if `datetime1 > datetime2`, `-1` if `datetime1 < datetime2`, and `0` if `datetime1 == datetime2`.
The `format1` and `format2` parameters must not have an offset or time zone.

Example:
```json5
DS.LocalDateTime.compare("2019-07-04T21:00:00", "yyyy-MM-dd'T'HH:mm:ss", "2019-07-04T21:00:00", "yyyy-MM-dd'T'HH:mm:ss")
```
Output:
```json5
0
```
---
## JsonPath
### `select(json:object, path:string):any`

Evaluates JsonPath expression and returns the resulting JSON object.
It uses the https://github.com/json-path/JsonPath[Jayway JsonPath implementation] and fully supports https://goessner.net/articles/JsonPath/[JsonPath specification].

Payload:
```json5
{
  "store": {
    "book": [
      {
        "category": "reference",
        "author": "Nigel Rees",
        "title": "Sayings of the Century",
        "price": 8.95
      },
      {
        "category": "fiction",
        "author": "Evelyn Waugh",
        "title": "Sword of Honour",
        "price": 12.99
      },
      {
        "category": "fiction",
        "author": "Herman Melville",
        "title": "Moby Dick",
        "isbn": "0-553-21311-3",
        "price": 8.99
      },
      {
        "category": "fiction",
        "author": "J. R. R. Tolkien",
        "title": "The Lord of the Rings",
        "isbn": "0-395-19395-8",
        "price": 22.99
      }
    ]
  }
}
```
Example:
```json5
{
    author: DS.JsonPath.select(payload, "$..book[-2:]..author")[0]
}
```
Output:
```json5
{
    "author": "Herman Melville"
}
```
---
## Util
### `select(obj:object, path:string):any`
Returns a value inside the object by given path separated by dot ('.').

Payload:
```json5
{
  "name": "Foo",
  "language": {
      "name": "Java",
      "version": "1.8"
  }
}
```
Example:
```json5
{
  language: DS.Util.select(payload, 'language.name')
}
```
Output:
```json5
{
   "language": "Java"
}
```
---
### `filterEx(objects:array, key:string, value:string, function filter_func=function(value1, value2) value1 == value2):array`
Filters array of objects by given condition.

Payload:
```json5
{
   "languages": [
     {
       "name": "Foo",
       "language": "Java"
     },
     {
       "name": "Bar",
       "language": "Scala"
     },
     {
       "name": "FooBar",
       "language": "Java"
     },
     {
       "name": "FooBar",
       "language": "C++"
     }
   ]
 }
```
Example:
```json5
{
  nonJavaLanguages: DS.Util.filterEx(payload.languages, 'language', 'Java', function(x, y) x != y)
}
```
Output:
```json5
[
 {
   "name": "Bar",
   "language": "Scala"
 },
 {
   "name": "FooBar",
   "language": "C++"
 }
]
```
---
### `groupBy(arr:array, keyName:string):object`
Partitions an array into a Object that contains Arrays, according to the discriminator key you define.
The discriminator can be a path inside the objects to group, e.g. 'language.name'

Payload:
```json5
{
  "languages": [
    {
      "name": "Foo",
      "language": {
          "name": "Java",
          "version": "1.8"
      }
    },
    {
      "name": "Bar",
      "language": {
          "name": "Scala",
          "version": "1.0"
      }
    },
    {
      "name": "FooBar",
      "language": {
          "name": "Java",
          "version": "1.7"
      }
    }
  ]
}
```
Example:
```json5
{
  languageGroups: DS.Util.groupBy(payload.languages, 'language.name')
}
```
Output:
```json5
{
    "languageGroups": {
       "Java": [
          {
             "language": {
                "name": "Java",
                "version": "1.8"
             },
             "name": "Foo"
          },
          {
             "language": {
                "name": "Java",
                "version": "1.7"
             },
             "name": "FooBar"
          }
       ],
       "Scala": [
          {
             "language": {
                "name": "Scala",
                "version": "1.0"
             },
             "name": "Bar"
          }
       ]
    }
}
```
---
### `remove(obj:object, keyName:string):object`
Removes a property with given name from the object and returns the remaining object

Payload:
```json5
{
   "availableSeats": 45,
   "airlineName": "Delta",
   "aircraftBrand": "Boeing",
   "aircraftType": "717",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
```
Example:
```json5
DS.Util.remove(payload, 'availableSeats')
```
Output:
```json5
{
   "airlineName": "Delta",
   "aircraftBrand": "Boeing",
   "aircraftType": "717",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
```
---
### `removeAll(obj:object, keyNames:array):object`
Removes all properties with names from a provided list of strings from the object and returns the remaining object

Payload:
```json5
{
   "availableSeats": 45,
   "airlineName": "Delta",
   "aircraftBrand": "Boeing",
   "aircraftType": "717",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
```
Example:
```json5
DS.Util.removeAll(payload, ['availableSeats', 'aircraftType', 'aircraftBrand'])
```
Output:
```json5
{
   "airlineName": "Delta",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
```
---
### `deepFlattenArrays(arr:array):array`
Flattens multiple nested arrays into a single array.

Payload:
```json5
[
    1,
    2,
    [
      3
    ],
    [
      4,
      [
        5,
        6,
        7
      ],
      {
        "x": "y"
      }
    ]
]
```
Example:
```json5
DS.Util.flattenArrays(payload)
```
Output:
```json5
[
     1,
     2,
     4,
     5,
     6,
     7,
     {
        "x": "y"
     }
]
```
---
### `reverse(arr:array):array`
Returns an array with elements in reverse order.

Payload:
```json5
[
    "a",
    "b",
    "c",
    "d"
]
```
Example:
```json5
DS.Util.reverse(payload)
```
Output:
```json5
[
    "d",
    "c",
    "b",
    "a",
]
```
---
### `parseDouble(str:string):number`
Parses a string which contains a double number and returns its numeric representation

Payload:
```json5
{
    "numberAsString": "123.45679"
}
```
Example:
```json5
{
    num: DS.Util.parseDouble(payload.numberAsString)
}
```
Output:
```json5
{
    "num": 123.45679
}
```
---
### `duplicates(arr:array, keyF=id:function, set=true:boolean):array`
Returns an array containing duplicate elements from input array. An optional key function returns a value which will be used as a comparison key. If `set` parameter is set to true, only the first duplicate value will be included.

Payload:
```json5
[
    {
      "language": {
        "name": "Java8",
        "version": "1.8"
      }
    },
    {
      "language": {
        "name": "Java8",
        "version": "1.8.0"
      }
    },
    {
      "language": {
        "name": "Scala",
        "version": "2.13.0"
      }
    }
]
```
Example:
```json5
DS.Util.duplicates(payload, function(x) x.language.name)
```
Output:
```json5
[
  {
    "language": {
      "name": "Java8",
      "version":"1.8.0"
    }
  }
]
```
---
### `sum(arr:array):number`
Returns sum of all elements in the array.

Payload:
```json5
[ 10, 20, 30 ]
```
Example:
```json5
DS.Util.sum(payload)
```
Output:
```json5
60
```
---
### `round(num:number, precision:number):number`
Rounds a double to the number of digits after the decimal point

Payload
```json5
{
    "num": 123.562567558
}
```
Example:
```json5
DS.Util.round(payload.num, 6)
```
Output:
```json5
123.562568
```
---
### `counts(arr:array, keyF=id:function):object`
Returns an object where keys are the results of calling keyF on the values, and the values are the counts of values that produced the corresponding key.

Payload
```json5
[
    {
      "name": "Foo",
      "language": {
        "name": "Java",
        "version": "1.8"
      }
    },
    {
      "name": "Bar",
      "language": {
        "name": "Scala",
        "version": "1.0"
      }
    },
    {
      "name": "FooBar",
      "language": {
        "name": "Java",
        "version": "1.7"
      }
    }
  ]
```
Example:
```json5
DS.Util.counts(payload, function(x) x.language.name);
```
Output:
```json5
{
    "Java": 2,
    "Scala": 1
}
```
---
### `mapToObject(arr, keyF, valueF=id):object`
Maps an array into an object, where the keys are the result of calling keyF on each value (which becomes the value at the key). If valueF is provided it gets run on the value. Duplicate keys are removed.

Payload
```json5
[
    {
      "name": "Foo",
      "language": {
        "name": "Java",
        "version": "1.8"
      }
    },
    {
      "name": "Bar",
      "language": {
        "name": "Scala",
        "version": "1.0"
      }
    },
    {
      "name": "FooBar",
      "language": {
        "name": "C++",
        "version": "n/a"
      }
    }
  ]
```
Example:
```json5
DS.Util.mapToObject(payload, function(x) x.language.name, function(v) v.language);
```
Output:
```json5
{
 "Java": {
   "name": "Java",
   "version": "1.8"
 },
 "C++": {
   "name": "C++",
   "version": "n/a"
 },
 "Scala": {
   "name": "Scala",
   "version": "1.0"
 }
};
```
---
## Regex
### `regexFullMatch(pattern:string, input:string):object`
Matches the entire input against the pattern (anchored start and end). If there's no match, returns `null`. If there's a match, returns a JSON object which has the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

Example:
```json5
DS.Regex.regexFullMatch(@'h(?P<mid>.*)o', 'hello');
```
Output:
```json5
{
  "captures": [
    "ell"
  ],
  "namedCaptures": {
    "mid": "ell"
  },
  "string": "hello"
}
```
---
### `regexPartialMatch(pattern:string, input:string):object`
Matches the input against the pattern (unanchored). If there's no match, returns `null`. If there's a match, returns a JSON object which has the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

Example:
```json5
DS.Regex.regexPartialMatch(@'e(?P<mid>.*)o', 'hello')
```
Output:
```json5
{
  "captures": [
    "ll"
  ],
  "namedCaptures": {
    "mid": "ll"
  },
  "string": "ello"
}
```
---
### `regexScan(pattern:string, input:string):object`
Finds all matches of the input against the pattern. If there are any matches, returns an array of JSON objects which have the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

Example:
```json5
DS.Regex.regexScan(@'(?P<user>[a-z]*)@(?P<domain>[a-z]*).org', 'modus@datasonnet.org,box@datasonnet.org')
```
Output:
```json5
[
    {
        "captures": [
            "modus",
            "datasonnet"
        ],
        "namedCaptures": {
            "domain": "datasonnet",
            "user": "modus"
        },
        "string": "modus@datasonnet.org"
    },
    {
        "captures": [
            "box",
            "datasonnet"
        ],
        "namedCaptures": {
            "domain": "datasonnet",
            "user": "box"
        },
        "string": "box@datasonnet.org"
    }
]
```
---
### `regexQuoteMeta(str:string):string`
Returns a literal pattern string for the specified string.

Example:
```json5
DS.Regex.regexQuoteMeta(@'1.5-2.0?')
```
Output:
```json5
"1\\.5-2\\.0\\?"
```
---
### `regexReplace(str:string, pattern:string, replacement:string):string`
Returns the input with the first match replaced by `replacement` string.

Example:
```json5
DS.Regex.regexReplace('wishyfishyisishy', @'ish', 'and')
```
Output:
```json5
"wandyfishyisishy"
```
---
### `regexGlobalReplace(str:string, pattern:string, replacement:string):string`
Returns the input with all matches replaced by `replacement` string.

Example:
```json5
DS.Regex.regexGlobalReplace('wishyfishyisishy', @'ish', 'and')
```
Output:
```json5
"wandyfandyisandy"
```
---
### `regexGlobalReplace(str:string, pattern:string, replacement:function):string`
Returns the input with all matches replaced by the result of the `replacement` function. The function must return string result and take a single object as an argument in the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

Example:
```json5
local square(obj) = std.toString(std.pow(std.parseInt(obj.string), 2));

DS.Regex.regexGlobalReplace("xxx2yyy4zzz6aaa", "\\d", square)
```
```json5
"xxx4yyy16zzz36aaa"
```
---
## URL
### `encode(data:string, string encoding="UTF-8"):string`
Translates a string into `application/x-www-form-urlencoded` format using the supplied encoding scheme to obtain the bytes for unsafe characters. The default encoding is `UTF-8`.

Example:
```json5
DS.URL.encode('Hello World')
```
Output:
```json5
"Hello+World"
```
---
### `decode(data:string, string encoding="UTF-8"):string`
Decodes a application/x-www-form-urlencoded string using a specific encoding scheme. The supplied encoding is used to determine what characters are represented by any consecutive sequences of the form "%xy".

Example:
```json5
DS.URL.decode('Hello+World')
```
Output:
```json5
"Hello World"
```
---
# DW
## Core
### `abs(n:number):number`
Returns the absolute value of a number.
Example:
```json5
[ DW.Core.abs(-2), DW.Core.abs(2.5), DW.Core.abs(-3.4), DW.Core.abs(3) ]
```
Output:
```json5
[ 2, 2.5, 3.4, 3 ]
```
---
### `avg(arr:array):number`
Returns the average of numbers listed in an array.

An array that is empty or that contains a non-numeric value results in an error.

Example:
```json5
{ a: DW.Core.avg([1, 1000]), b: DW.Core.avg([1, 2, 3]) }
```
Output:
```json5
{ "a": 500.5, "b": 2.0 }
```
---
### `ceil(n:number):number`
Rounds a number up to the nearest whole number.

Example:
```json5
[ DW.Core.ceil(1.5), DW.Core.ceil(2.1), DW.Core.ceil(3) ]
```
Output:
```json5
[ 2, 3, 3 ]
```
---
### `contains(arr:array, any:any):boolean`
Returns `true` if an input contains a given value, `false` if not.

This version of `contains` accepts an array as input. Other versions accept a string and can use another string or regular expression to determine whether there is a match.

Example:
```json5
DW.Core.contains([ 1, 2, 3, 4 ], 2)
```
Output:
```json5
true
```
---
### `daysBetween(d1:date, d2:date):number`
Returns the number of days between two dates.

Example:
```json5
DW.Core.daysBetween('2016-10-01T23:57:59-03:00', '2017-10-01T23:57:59-03:00') //error
```
Output:
```json5
365
```
---
### `distinctBy(arr:array, funct(it,ind):function):array`
Iterates over an array and returns the unique elements in it.

This version of `distinctBy` finds unique values in an array. Other versions act on an object and handle a null value.

Example:
```json5
DW.Core.distinctBy([0, 1, 2, 3, 3, 2, 1, 4], function(it,ind) it)
```
Output:
```json5
[ 0, 1, 2, 3, 4]
```
---
### `endsWith(str1:string, str2:string):boolean`
Returns `true` if a string ends with a provided substring, `false` if not.

Example:
```json5
DW.Core.endsWith("Hello World!", "!")
```
Output:
```json5
true
```
---
### `entriesOf(obj:object):object`
Returns an array of key-value pairs that describe the key, value, and any attributes in the input object.
Example:
```json5
DW.Core.entriesOf({testK:  "testV"})
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
### `filter(arr:array, funct(it,ind):function):array`
Iterates over an array and applies an expression that returns matching values.

The expression must return `true` or `false`. If the expression returns `true` for a value or index in the array, the value gets captured in the output array. If it returns `false` for a value or index in the array, that item gets filtered out of the output. If there are no matches, the output array will be empty.

Example:
```json5
DW.Core.filter([9,2,3,4,5], function(it,ind) it > 2)
```
Output:
```json5
[9,3,4,5]
```
---
### `filterObject(obj:object, funct(v,k,ind):function):object`
Iterates a list of key-value pairs in an object and applies an expression that returns only matching objects, filtering out the rest from the output.

The expression must return `true` or `false`. If the expression returns `true` for a key, value, or index of an object, the object gets captured in the output. If it returns `false` for any of them, the object gets filtered out of the output. If there are no matches, the output array will be empty.

Example:
```json5
local obj ={
  "testK": "testV",
  "test2": "value2"
};

{
  ind: DW.Core.filterObject(obj, function(value,key,index) index == 0),
  key: DW.Core.filterObject(obj, function(value,key,index) key == "testK")
}
```
Output:
```json5
{
  "ind": {
    "test2": "value2"
  },
  "key": {
    "testK": "testV"
  },
}
```
---
### `find(arr:array, any:any):array`
Returns indices of an input that match a specified value.

This version of the function returns indices of an array. Others return indices of a string.

Example:
```json5
DW.Core.find(["Bond", "James", "Bond"], "Bond")
```
Output:
```json5
[0,2]
```
---
### `flatMap(arr:array, funct(it,ind):function):array`
Iterates over each item in an array and flattens the results.

Instead of returning an array of arrays (as `map` does when you iterate over the 
values within an input like `[ [1,2], [3,4] ]`), `flatMap` returns a flattened 
array that looks like this: `[1, 2, 3, 4]`. `flatMap` is similar to `flatten`, 
but `flatten` only acts on the values of the arrays, while `flatMap` can act on 
values and indices of items in the array.

Example:
```json5
DW.Core.flatMap([[3,5],[1,2,5]], function(v,i) v)
```
Output:
```json5
[3,5,1,2,5]
```
---
### `flatten(arr:array):array`
Turns a set of subarrays (such as `[ [1,2,3], [4,5,[6]], [], [null] ]`) into a 
single, flattened array (such as `[ 1, 2, 3, 4, 5, [6], null ]`).

Note that it flattens only the first level of subarrays and omits empty subarrays.

Example:
```json5
DW.Core.flatten([ [1,2,3], [4,5,[6]], [], [null] ])
```
Output:
```json5
[ 1, 2, 3, 4, 5, [6], null ]
```
---
### `floor(n:number):number`
Rounds a number down to the nearest whole number.

Example:
```json5
DW.Core.floor(0.22)
```
Output:
```json5
0
```
### `groupBy(arr:array, funct(it,ind):function):object`
Returns an object that groups items from an array based on specified criteria, 
such as an expression or matching selector.

This version of `groupBy` groups the elements of an array using the `criteria` function. 
Other versions act on objects and handle null values.

Example:
```json5
local input = [
  { "name": "Foo", "language": "Java" },
  { "name": "Bar", "language": "Scala" },
  { "name": "FooBar", "language": "Java"}
];

DW.Core.groupBy(input, function(it,idx) it.language)
```
Output:
```json5
{
  "Java":[
    {"name":"Foo","language":"Java"},
    {"name":"FooBar","language":"Java"}
  ],
  "Scala":[
    {"name":"Bar","language":"Scala"}
  ]
}
```
---
### `isBlank(n:string):boolean`
Returns `true` if the given string is empty or completely composed of whitespace, `false` if not.

Example:
```json5
[DW.Core.isBlank(""),DW.Core.isBlank("    "), DW.Core.isBlank(null)]
```
Output:
```json5
[true,true,true]
```
---
### `isDecimal(n:number):boolean`
Returns `true` if the given number contains a decimal, `false` if not.

Example:
```json5
DW.Core.isDecimal(0.22)
```
Output:
```json5
true
```
---
### `isEmpty(n:any):boolean`
Returns `true` if the given input value is empty, `false` if not.

This version of `isEmpty` acts on an array. Other versions act on a string or object, and handle null values.

Example:
```json5
[DW.Core.isEmpty(""),
DW.Core.isEmpty({}),
DW.Core.isEmpty([]),
DW.Core.isEmpty(null)]
```
Output:
```json5
[true,true,true,true]
```
---
### `isEven(n:number):boolean`
Returns `true` if the number or numeric result of a mathematical operation is even, `false` if not.

Example:
```json5
DW.Core.isEven(50)
```
Output:
```json5
true
```
---
### `isInteger(n:number):boolean`
Returns true if the given number is an integer (which lacks decimals), false if not.

Example:
```json5
DW.Core.isInteger(50)
```
Output:
```json5
true
```
---
### `isLeapYear(n:date):boolean`
Returns true if it receives a date for a leap year, false if not.

This version of leapYear acts on a DateTime type. Other versions act on the other date and time formats that DataWeave supports.

Example:
```json5
DW.Core.isLeapYear(|2017-10-01T23:57:59|) //error
```
Output:
```json5
false
```
---
### `isOdd(n:number):boolean`
Returns true if the number or numeric result of a mathematical operation is odd, false if not.

Example:
```json5
DW.Core.isOdd(5)
```
Output:
```json5
true
```
---
### `joinBy(arr:array, str:string):string`
Merges an array into a single string value and uses the provided string as a separator between each item in the list.

Note that joinBy performs the opposite task of splitBy.

Example:
```json5
DW.Core.joinBy([1,2,3,4], "-")
```
Output:
```json5
"1-2-3-4"
```
---
### `keysOf(obj:object):array`
Returns an array of keys from key-value pairs within the input object.

Example:
```json5
DW.Core.keysOf({test: 1})
```
Output:
```json5
["test"]
```
---
### `log(str:string):nothing`
Without changing the value of the input, log returns the input as a system log.

Use this function to help with debugging DataWeave scripts. A Mule app outputs the results through the DefaultLoggingService, which you can see in the Studio console.

Example:
```json5
DW.Core.log("nothing") //error
```
---
### `lower(str:string):string`
Returns the provided string in lowercase characters.

Example:
```json5
DW.Core.lower("Hello World!")
```
Output:
```json5
"hello world!"
```
---
### `map(arr:array, funct(it,ind):function):array`
Iterates over items in an array and outputs the results into a new array.

Example:
```json5
DW.Core.map(['a','b','c'], function(value,index) value+'_'+index
```
Output:
```json5
["a_0", "b_1", "c_2"]
```
---
### `mapObject(obj:object, funct(v,k,ind):function):object`
Iterates over an object using a mapper that acts on keys, values, or indices of that object.

Example:
```json5
DW.Core.mapObject({"a":"b","c":"d"}, function(v,k,i) {[v]:{ [k]: i}})
```
Output:
```json5
{"b":{"a":0},"d":{"c":1}}
```
---
### `match(str:string, regex:string):array`
Uses a Java regular expression (regex) to match a string and then separates it into capture groups. Returns the results in an array.

Note that you can use match for pattern matching expressions that include case statements.

Example:
```json5
DW.Core.match("m2@mulesoft.com", "([a-z]*)@([a-z]*).com")
```
Output:
```json5
["me@mulesoft.com","me","mulesoft"]
```
---
### `matches(str:string, regex:string):boolean`
Checks if an expression matches the entire input string.

For use cases where you need to output or conditionally process the matched value, see Pattern Matching in DataWeave.

Example:
```json5
DW.Core.matches("admin123", "a.*\\d+")
```
Output:
```json5
true
```
---
### `max(a:array):any`
Returns the highest Comparable value in an array.

The items must be of the same type, or the function throws an error. The function returns null if the array is empty.

Example:
```json5
[
  DW.Core.max([false,true,false]), 
  DW.Core.max(["a", "b" , "c"]),
  DW.Core.max([5,1,88,3])
]
```
Output:
```json5
[true, "c", 88]
```
---
### `maxBy(a:array, funct(i):function):any`
Iterates over an array and returns the highest value of Comparable elements from it.

The items must be of the same type. maxBy throws an error if they are not, and the function returns null if the array is empty.

Example:
```json5
[
  DW.Core.maxBy([false,true,false],function(i) i), 
  DW.Core.maxBy(["a", "b" , "c"],function(i) i),
  DW.Core.maxBy([5,1,88,3],function(i) i)
]
```
Output:
```json5
[true, "c", 88]
```
---
### `min(a:array):any`
Returns the lowest Comparable value in an array.

The items must be of the same type or min throws an error. The function returns null if the array is empty.

Example:
```json5
[
  DW.Core.min([false,true,false]), 
  DW.Core.min(["a", "b" , "c"]),
  DW.Core.min([5,1,88,3])
]
```
Output:
```json5
[false, "a", 1]
```
---
### `minBy(a:array, funct(i):function):any`
Iterates over an array to return the lowest value of comparable elements from it.

The items need to be of the same type. minBy returns an error if they are not, and it returns null when the array is empty.

Example:
```json5
[
  DW.Core.minBy([false,true,false],function(i) i), 
  DW.Core.minBy(["a", "b" , "c"],function(i) i),
  DW.Core.minBy([5,1,88,3],function(i) i)
]
```
Output:
```json5
[false, "a", 1]
```
---
### `mod(n1:number, n2:number):number`
Returns the modulo (the remainder after dividing the dividend by the divisor).

Example:
```json5
DW.Core.mod(2,2)
```
Output:
```json5
0
```
---
### `namesOf(obj:object):array`
Returns an array of strings with the names of all the keys within the given object.

Example:
```json5
DW.Core.namesOf({ "a" : true, "b" : 1})
```
Output:
```json5
["a", "b"]
```
---
### `now():date`
Returns a DateTime value for the current date and time.

Example:
```json5
DW.Core.now() //error
```
Output:
```json5
""
```
---
### `orderBy(in:object|array, function((v,k)|(it,ind)):function):object|array`
Reorders the elements of an input using criteria that acts on selected elements of that input.

This version of orderBy takes an object as input. Other versions act on an input array or handle a null value.

Example:
```json5
DW.Core.orderBy([0,5,1,3,2,1], function(it,ind) it)
```
Output:
```json5
[0,1,1,2,3,5]
```
---
### `pluck(obj:object, function(v,k,ind):function):array`
Useful for mapping an object into an array, pluck iterates over an object and returns an array of keys, values, or indices from the object.

It is an alternative to mapObject, which is similar but returns an object, instead of an array.

Example:
```json5
DW.Core.pluck({"a":"b","c":"d"}, function(v,k,ind) ind)
```
Output:
```json5
[0,1]
```
---
### `pow(n1:number, n2:number):number`
Raises the value of a base number to the specified power.

Example:
```json5
DW.Core.pow(5,2)
```
Output:
```json5
25
```
---
### `random():number`
Returns a pseudo-random number greater than or equal to 0.0 and less than 1.0.

Example:
```json5
DW.Core.random()
```
Output:
```json5
0.7
```
---
### `randomInt(n:number):number`
Returns a pseudo-random whole number from 0 to the specified number (exclusive).

Example:
```json5
DW.Core.randomInt(10)
```
Output:
```json5
5
```
---
### `read(str:string):any`
Reads a string or binary and returns parsed content.

This function can be useful if the reader cannot determine the content type by default.

Example:
```json5
DW.Core.read("") //error
```
Output:
```json5
""
```
---
### `readUrl(str:string, type:string):any`
Similar to the read function. However, readURL accepts a URL, including a classpath-based URL.

The classpath-based URL uses the classpath:` protocol prefix, for example, classpath://myfolder/myFile.txt where myFolder is located under src/main/resources in a Mule project. Otherwise, readURL accepts the same arguments as read.

Example:
```json5
DW.Core.readUrl("http://httpbin.org/get?test=true").args
```
Output:
```json5
{
  "test": "true"
}
```
---
### `reduce(arr:array, funct(it,acc):function,initial:any):any`
Applies a reduction expression to the elements in an array.

For each element of the input array, in order, reduce applies the reduction lambda expression (function), then replaces the accumulator with the new result. The lambda expression can use both the current input array element and the current accumulator value.

Note that if the array is empty and no default value is set on the accumulator parameter, a null value is returned.

Example:
```json5
DW.Core.reduce([2.3], function(it,acc) it+acc, 0)
```
Output:
```json5
5
```
---
### `replace(str:string, regex:string, replacement:string):string`
Performs string replacement.

This version of replace accepts a Java regular expression for matching part of a string. It requires the use of the with helper function to specify a replacement string for the matching part of the input string.

Example:
```json5
DW.Core.replace("123-456-7890", ".*-", "")
```
Output:
```json5
"7890"
```
---
### `round(n:number):number`
Rounds a number up or down to the nearest whole number.

Example:
```json5
DW.Core.round(0.22)
```
Output:
```json5
0
```
---
### `scan(str:string, regex:string):array`
Returns an array with all of the matches found in an input string.

Each match is returned as an array that contains the complete match followed by any capture groups in your regular expression (if present).

Example:
```json5
DW.Core.scan("anypt@mulesoft.com,max@mulesoft.com", "([a-z]*)@([a-z]*).com")
```
Output:
```json5
[
  ["anypt@mulesoft.com","anypt","mulesoft"],
  ["max@mulesoft.com","max","mulesoft"]
]
```
---
### `sizeOf(in:array|object|string):number`
Returns the number of elements in an array. It returns 0 if the array is empty.

This version of sizeOf takes an array or an array of arrays as input. Other versions act on arrays of objects, strings, or binary values.

Example:
```json5
{
  str: DW.Core.sizeOf("Hello World!"),
  array: DW.Core.sizeOf([1,2]),
  obj: DW.Core.sizeOf({a: 1})
}
```
Output:
```json5
{
  "str": 12,
  "array": 2,
  "obj": 1
}
```
---
### `splitBy(str:string, regex:string):array`
Splits a string into a string array based on a value that matches part of that string. It filters out the matching part from the returned array.

This version of splitBy accepts a Java regular expression (regex) to match the input string. The regex can match any character in the input string. Note that splitBy performs the opposite operation of joinBy.

Example:
```json5
{
  str: DW.Core.splitBy("a-b-c-d","-"),
  regex: DW.Core.splitBy("a-b-c-d","[b]")
}
```
Output:
```json5
{
  "str": ["a", "b", "c"],
  "regex": ["a-", "-c"]
}
```
---
### `sqrt(n:number):number`
Returns the square root of a number.

Example:
```json5
DW.Core.sqrt(4)
```
Output:
```json5
2
```
---
### `startsWith(str:string, sub:string):boolean`
Returns true or false depending on whether the input string starts with a matching prefix.

Example:
```json5
DW.Core.startsWith("Hello World!", "Hell")
```
Output:
```json5
true
```
---
### `sum(arr:array):number`
Returns the sum of numeric values in an array.

Returns 0 if the array is empty and produces an error when non-numeric values are in the array.

Example:
```json5
DW.Core.sum([2,7,4,8,3,2,2])
```
Output:
```json5
28
```
---
### `to(start:number, end:number):array`
Returns a range with the specified boundaries.

The upper boundary is inclusive.

Example:
```json5
DW.Core.to(0,3)
```
Output:
```json5
[0,1,2,3]
```
---
### `trim(str:string):string`
Removes any blank spaces from the beginning and end of a string.

Example:
```json5
DW.Core.trim("   Hello   World   ")
```
Output:
```json5
"Hello   World"
```
---
### `typeOf(in:any):string`
Returns the type of a value.

Example:
```json5
{
  string: DW.Core.typeOf(""),
  "null": DW.Core.typeOf(null),
  bool: DW.Core.typeOf(true),
  object: DW.Core.typeOf({}),
  array: DW.Core.typeOf([])
}
```
Output:
```json5
{
  "string": "string",
  "null": "null",
  "bool": "boolean",
  "object": "object",
  "array": "array"
}
```
---
### `unzip(arr:array):array`
Performs the opposite of zip. It takes an array of arrays as input.

The function groups the values of the input sub-arrays by matching indices, and it outputs new sub-arrays with the values of those matching indices. No sub-arrays are produced for unmatching indices. For example, if one input sub-array contains four elements (indices 0-3) and another only contains three (indices 0-2), the function will not produce a sub-array for the value at index 3.

Example:
```json5
DW.Core.unzip([[0,"a"],[1,"b"],[2,"c"],[ 3,"d"]])
```
Output:
```json5
[[0,1,2,3],["a","b","c","d"]]
```
---
### `upper(str:string):string`
Returns the provided string in uppercase characters.

Example:
```json5
DW.Core.upper("Hello World!")
```
Output:
```json5
"HELLO WORLD!"
```
---
### `uuid():string`
Returns a v4 UUID using random numbers as the source.

Example:
```json5
DW.Core.uuid()
```
Output:
```json5
"7cc64d24-f2ad-4d43-8893-fa24a0789a99"
```
---
### `valuesOf(obj:object):array`
Returns an array of the values from key-value pairs in an object.

Example:
```json5
DW.Core.valuesOf({a: true, b: 1})
```
Output:
```json5
[true,1]
```
---
### `zip(arr1:array, arr2:array):array`
Merges elements from two arrays into an array of arrays.

The first sub-array in the output array contains the first indices of the input sub-arrays. The second index contains the second indices of the inputs, the third contains the third indices, and so on for every case where there are the same number of indices in the arrays.

Example:
```json5
DW.Core.zip([0,1], ["a","b"])
```
Output:
```json5
[ [0,"a"], [1,"b"] ]
```
---