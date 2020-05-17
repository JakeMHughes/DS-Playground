# std

Standard Library
This page describes the functions available in Jsonnet's standard library, i.e. the object implicitly bound to the std variable. Some of the standard library functions can be implemented in Jsonnet. Their code can be found in the std.jsonnet file. The behavior of some of the other functions, i.e. the ones that expose extra functionality not otherwise available to programmers, is described formally in the specification.

The standard library is implicitly added to all Jsonnet programs by enclosing them in a local construct. For example, if the program given by the user is {x: "foo"}, then the actual code executed would be local std = { ... }; {x: "foo"}. The functions in the standard library are all hidden fields of the std object.

---
## External Variables
### std.extVar(x)
If an external variable with the given name was defined, return its string value. Otherwise, raise an error.

---
## Types and Reflection
### std.thisFile():string
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
### std.type(x)
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
### std.length(x)
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
### std.objectHas(o, f)
Returns true if the given object has the field (given as a string), otherwise false. Raises an error if the arguments are not object and string respectively. Returns false if the field is hidden.

---
### std.objectFields(o)
Returns an array of strings, each element being a field from the given object. Does not include hidden fields.

---
### std.objectHasAll(o, f)
As std.objectHas but also includes hidden fields.

---
### std.objectFieldsAll(o)
As std.objectFields but also includes hidden fields.

---
### std.prune(a)
Recursively remove all "empty" members of a. "Empty" is defined as zero length `arrays`, zero length `objects`, or `null` values. The argument a may have any type.

---
### std.mapWithKey(func, obj)
Apply the given function to all fields of the given object, also passing the field name. The function func is expected to take the field name as the first parameter and the field value as the second.

---
## Mathematical Utilities
The following mathematical functions are available:

---
### std.abs(n)

---
### std.sign(n)

---
### std.max(a, b)

---
### std.min(a, b)

---
### std.pow(x, n)

---
### std.exp(x)

---
### std.log(x)

---
### std.exponent(x)

---
### std.mantissa(x)

---
### std.floor(x)

---
### std.ceil(x)

---
### std.sqrt(x)

---
### std.sin(x)

---
### std.cos(x)

---
### std.tan(x)

---
### std.asin(x)

---
### std.acos(x)

---
### std.atan(x)
The function std.mod(a, b) is what the % operator is desugared to. It performs modulo arithmetic if the left hand side is a number, or if the left hand side is a string, it does Python-style string formatting with std.format().

---
### std.clamp(x, minVal, maxVal)
Available since version 0.15.0.
Clamp a value to fit within the range [minVal, maxVal]. Equivalent to std.min(minVal, std.max(x, maxVal)).

Example: std.clamp(-3, 0, 5) yields 0.

Example 2: std.clamp(4, 0, 5) yields 4.

Example 3: std.clamp(7, 0, 5) yields 5.

---
## Assertions and Debugging
### std.assertEqual(a, b)
Ensure that a == b. Returns true or throws an error message.

---
## String Manipulation
### std.toString(a)
Convert the given argument to a string.

---
### std.codepoint(str)
Returns the positive integer representing the unicode codepoint of the character in the given single-character string. This function is the inverse of std.char(n).

---
### std.char(n)
Returns a string of length one whose only unicode codepoint has integer id n. This function is the inverse of std.codepoint(str).

---
### std.substr(str, from, len)
Returns a string that is the part of s that starts at offset from and is len codepoints long. If the string s is shorter than from+len, the suffix starting at position from will be returned.

---
### std.findSubstr(pat, str)
Returns an array that contains the indexes of all occurances of pat in str.

---
### std.startsWith(a, b)
Returns whether the string a is prefixed by the string b.

---
### std.endsWith(a, b)
Returns whether the string a is suffixed by the string b.

---
### std.stripChars(str, chars)
Available since version 0.15.0.
Removes characters chars from the beginning and from the end of str.

Example: std.stripChars(" test test test ", " ") yields "test test test".

Example: std.stripChars("aaabbbbcccc", "ac") yields "bbbb".

Example: std.stripChars("cacabbbbaacc", "ac") yields "bbbb".

---
### std.lstripChars(str, chars)
Available since version 0.15.0.
Removes characters chars from the beginning of str.

Example: std.lstripChars(" test test test ", " ") yields "test test test ".

Example: std.lstripChars("aaabbbbcccc", "ac") yields "bbbbcccc".

Example: std.lstripChars("cacabbbbaacc", "ac") yields "bbbbaacc".

---
### std.rstripChars(str, chars)
Available since version 0.15.0.
Removes characters chars from the beginning and from the end of str.

Example: std.rstripChars(" test test test ", " ") yields " test test test".

Example: std.rstripChars("aaabbbbcccc", "ac") yields "aaabbbb".

Example: std.rstripChars("cacabbbbaacc", "ac") yields "cacabbbb".

---
### std.split(str, c)
Split the string str into an array of strings, divided by the single character c.

Example: std.split("foo/bar", "/") yields ["foo", "bar"].

Example: std.split("/foo/", "/") yields ["", "foo", ""].

---
### std.splitLimit(str, c, maxsplits)
As std.split(str, c) but will stop after maxsplits splits, thereby the largest array it will return has length maxsplits + 1. A limit of -1 means unlimited.

Example: std.splitLimit("foo/bar", "/", 1) yields ["foo", "bar"].

Example: std.splitLimit("/foo/", "/", 1) yields ["", "foo/"].

---
### std.strReplace(str, from, to)
Returns a copy of the string in which all occurrences of string from have been replaced with string to

Example: std.strReplace('I like to skate with my skateboard', 'skate', 'surf') yields "I like to surf with my surfboard".

---
### std.asciiUpper(str)
Returns a copy of the string in which all ASCII letters are capitalized.

Example: std.asciiUpper('100 Cats!') yields "100 CATS!".

---
### std.asciiLower(str)
Returns a copy of the string in which all ASCII letters are lower cased.

Example: std.asciiLower('100 Cats!') yields "100 cats!".

---
### std.stringChars(str)
Split the string str into an array of strings, each containing a single codepoint.

Example: std.stringChars("foo") yields ["f", "o", "o"].

---
### std.format(str, vals)
Format the string str using the values in vals. The values can be an array, an object, or in other cases are treated as if they were provided in a singleton array. The string formatting follows the same rules as Python. The % operator can be used as a shorthand for this function.

Example: std.format("Hello %03d", 12) yields "Hello 012".

Example: "Hello %03d" % 12 yields "Hello 012".

Example: "Hello %s, age %d" % ["Foo", 25] yields "Hello Foo, age 25".

Example: "Hello %(name)s, age %(age)d" % {age: 25, name: "Foo"} yields "Hello Foo, age 25".

---
### std.escapeStringBash(str)
Wrap str in single quotes, and escape any single quotes within str by changing them to a sequence '"'"'. This allows injection of arbitrary strings as arguments of commands in bash scripts.

---
### std.escapeStringDollars(str)
Convert $ to $$ in str. This allows injection of arbitrary strings into systems that use $ for string interpolation (like Terraform).

---
### std.escapeStringJson(str)
Convert str to allow it to be embedded in a JSON representation, within a string. This adds quotes, escapes backslashes, and escapes unprintable characters.

Example:
{ local description = "Multiline\nc:\\path", json: "{name: %s}" % std.escapeStringJson(description) }
yields: {"json": "{name: \"Multiline\\nc:\\\\path\"}"}

---
### std.escapeStringPython(str)
Convert str to allow it to be embedded in Python. This is an alias for std.escapeStringJson.

---
## Parsing
### std.parseInt(str)
Parses a signed decimal integer from the input string.

Example: std.parseInt("123") yields 123.

Example: std.parseInt("-123") yields -123.

---
### std.parseOctal(str)
Parses an unsigned octal integer from the input string. Initial zeroes are tolerated.

Example: std.parseOctal("755") yields 493.

---
### std.parseHex(str)
Parses an unsigned hexadecimal integer, from the input string. Case insensitive.

Example: std.parseHex("ff") yields 255.

---
### std.parseJson(str)
Available since version 0.13.0.
Parses a JSON string.

Example: std.parseJson('{"foo": "bar"}') yields {"foo": "bar"}.

---
### std.encodeUTF8(str)
Available since version 0.13.0.
Encode a string using UTF8. Returns an array of numbers representing bytes.

---
### std.decodeUTF8(arr)
Available since version 0.13.0.
Decode an array of numbers representing bytes using UTF8. Returns a string.

---
## Manifestation
### std.manifestIni(ini)
Convert the given structure to a string in INI format. This allows using Jsonnet's object model to build a configuration to be consumed by an application expecting an INI file. The data is in the form of a set of sections, each containing a key/value mapping. These examples should make it clear:

{
    main: { a: "1", b: "2" },
    sections: {
        s1: {x: "11", y: "22", z: "33"},
        s2: {p: "yes", q: ""},
        empty: {},
    }
}
Yields a string containing this INI file:

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

---
### std.manifestPython(v)
Convert the given value to a JSON-like form that is compatible with Python. The chief differences are True / False / None instead of true / false / null.

{
  b: ["foo", "bar"],
  c: true,
  d: null,
  e: { f1: false, f2: 42 },
}
Yields a string containing Python code like:

{
  "b": ["foo", "bar"],
  "c": True,
  "d": None,
  "e": {"f1": False, "f2": 42}
}

---
### std.manifestPythonVars(conf)
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
### std.manifestJsonEx(value, indent)
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
### std.manifestYamlDoc(value)
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
### std.manifestYamlStream(value)
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
### std.manifestXmlJsonml(value)
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
### std.makeArray(sz, func)
Create a new array of sz elements by calling func(i) to initialize each element. Func is expected to be a function that takes a single parameter, the index of the element it should initialize.

Example: std.makeArray(3,function(x) x * x) yields [0, 1, 4].

---
### std.member(arr, x)
Available since version 0.15.0.
Returns whether x occurs in arr. Argument arr may be an array or a string.

---
### std.count(arr, x)
Return the number of times that x occurs in arr.

---
### std.find(value, arr)
Returns an array that contains the indexes of all occurances of value in arr.

---
### std.map(func, arr)
Apply the given function to every element of the array to form a new array.

---
### std.mapWithIndex(func, arr)
Similar to map above, but it also passes to the function the element's index in the array. The function func is expected to take the index as the first parameter and the element as the second.

---
### std.filterMap(filter_func, map_func, arr)
This is primarily used to desugar array comprehension syntax. It first filters, then maps the given array, using the two functions provided.

---
### std.flatMap(func, arr)
Apply the given function to every element of the array to form a new array then flatten the result. It can be thought of as a generalized map, where each element can get mapped to 0, 1 or more elements.

Example: std.flatMap(function(x) [x, x], [1, 2, 3]) yields [1, 1, 2, 2, 3, 3]).

Example: std.flatMap(function(x) if x == 2 then [] else [x], [1, 2, 3]) yields [1, 3]).

Example: std.flatMap(function(x) if x == 2 then [] else [x * 3, x * 2], [1, 2, 3]) yields [3, 2, 9, 6]).

---
### std.filter(func, arr)
Return a new array containing all the elements of arr for which the func function returns true.

---
### std.foldl(func, arr, init)
Classic foldl function. Calls the function on the result of the previous function call and each array element, or init in the case of the initial element. Traverses the array from left to right.

---
### std.foldr(func, arr, init)
Classic foldl function. Calls the function on each array element and the result of the previous function call, or init in the case of the initial element. Traverses the array from right to left.

---
### std.range(from, to)
Return an array of ascending numbers between the two limits, inclusively.

---
### std.repeat(what, count)
Available since version 0.15.0.
Repeats an array or a string what a number of times specified by an integer count.

Example: std.repeat([1, 2, 3], 3) yields [1, 2, 3, 1, 2, 3, 1, 2, 3].

Example 2: std.repeat("blah", 2) yields "blahblah".

---
### std.join(sep, arr)
If sep is a string, then arr must be an array of strings, in which case they are concatenated with sep used as a delimiter. If sep is an array, then arr must be an array of arrays, in which case the arrays are concatenated in the same way, to produce a single array.

Example1: std.join(".", ["www", "google", "com"]) yields "www.google.com".

Example2: std.join([9, 9], [[1], [2, 3]]) yields [1, 9, 9, 2, 3].

---
### std.lines(arr)
Concatenate an array of strings into a text file with newline characters after each string. This is suitable for constructing bash scripts and the like.

---
### std.flattenArrays(arrs)
Concatenate an array of arrays into a single array.

Example: std.flattenArrays([[1, 2], [3, 4], [[5, 6], [7, 8]]]) yields [1, 2, 3, 4, [5, 6], [7, 8]].

---
### std.reverse(arr)
Available since version 0.13.0.
Reverses an array.

---
### std.sort(arr, keyF=id)
Sorts the array using the <= operator.

Optional argument keyF is a single argument function used to extract comparison key from each array element. Default value is identity function keyF=function(x) x.

---
### std.uniq(arr, keyF=id)
Removes successive duplicates. When given a sorted array, removes all duplicates.

Optional argument keyF is a single argument function used to extract comparison key from each array element. Default value is identity function keyF=function(x) x.

---
## Sets
Sets are represented as ordered arrays without duplicates.

Note that the std.set* functions rely on the uniqueness and ordering on arrays passed to them to work. This can be guarenteed by using std.set(arr). If that is not the case, the functions will quietly return non-meaningful results.

All set.set* functions accept keyF function of one argument, which can be used to extract key to use from each element. All Set operations then use extracted key for the purpose of identifying uniqueness. Default value is identity function local id = function(x) x

---
### std.set(arr, keyF=id)
Shortcut for std.uniq(std.sort(arr)).

---
### std.setInter(a, b, keyF=id)
Set intersection operation (values in both a and b).

---
### std.setUnion(a, b, keyF=id)
Set union operation (values in any of a or b). Note that + on sets will simply concatenate the arrays, possibly forming an array that is not a set (due to not being ordered without duplicates).

Example1: std.setUnion([1, 2], [2, 3]) yields [1, 2, 3].

Example2: std.setUnion([{n:"A", v:1}, {n:"B"}], [{n:"A", v: 9999}, {n:"C"}],keyF=function(x) x.n) yields [{n: "A", v:1}, {n: "B"}, {n: "C"}].

---
### std.setDiff(a, b, keyF=id)
Set difference operation (values in a but not b).

---
### std.setMember(x, arr, keyF=id)
Returns true if x is a member of array, otherwise false.

---
## Encoding
### std.base64(input)
Encodes the given value into a base64 string. The encoding sequence is A-Za-z0-9+/ with = to pad the output to a multiple of 4 characters. The value can be a string or an array of numbers, but the codepoints / numbers must be in the 0 to 255 range. The resulting string has no line breaks.

---
### std.base64DecodeBytes(str)
Decodes the given base64 string into an array of bytes (number values). Currently assumes the input string has no linebreaks and is padded to a multiple of 4 (with the = character). In other words, it consumes the output of std.base64().

---
### std.base64Decode(str)
Deprecated, use std.base64DecodeBytes and decode the string explicitly (e.g. with std.decodeUTF8) instead.
Behaves like std.base64DecodeBytes() except returns a naively encoded string instead of an array of bytes.

---
### std.md5(s)
Encodes the given value into an MD5 string.

---
## JSON Merge Patch
### std.mergePatch(target, patch)
Applies patch to target according to RFC7396

---
## Debugging
### std.trace(str, rest)
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

### `hash(string value, string algorithm)`
Calculates hash of a String value using one of the supported algorithms. The `algorithm` must be one of `MD2`, `MD5`, `SHA-1`, `SHA-256`, `SHA-384`, `SHA-512`
The response is a string containing the hash bytes.

*Example:*

------------------------
{
    hashValue: DS.Crypto.hash("HelloWorld", "MD5")
}
------------------------
.Result
------------------------
{
    "hashValue": "68e109f0f40ca72a15e05cc22786f8e6"
}
------------------------
### `hmac(string value, string secret, string algorithm)`
Generates hash-based message autentication code using provided secret and a hash function algorithm. The `algoritm` must be one of `HmacSHA1`, `HmacSHA256` or `HmacSHA512`.

*Example:*

------------------------
{
    hmacValue: DS.Crypto.hmac("HelloWorld", "DataSonnet rules!", "HmacSHA256")
}
------------------------
.Result
------------------------
{
    "hmacValue": "7854220ef827b07529509f68f391a80bf87fff328dbda140ed582520a1372dc1"
}
------------------------

### `encrypt(string value, string key)`
Encrypts the string using the key and the Blowfish algorithm. The result is Base64-encoded encrypted string.

*Example:*

------------------------
{
    passwd: DS.Crypto.encrypt("HelloWorld", "DataSonnet123")
}
------------------------
.Result
------------------------
{
    "passwd": "HdK8opktKiK3ero0RJiYbA=="
}
------------------------

### `decrypt(string value, string key)`
Decrypts the string using the key and the Blowfish algorithm.

*Example:*

------------------------
{
    passwd: DS.Crypto.decrypt(payload.encryptedPassword, "MySecretKey")
}
------------------------

## ZonedDateTime

### `now()`
Returns the current date/time from the system UTC clock in ISO-8601 format.

*Example*

------------------------
{
    currentZuluTime: DS.ZonedDateTime.now()
}
------------------------

.Result:
------------------------
{
    "currentZuluTime": "2019-08-19T18:58:38.313Z"
}
------------------------

### `offset(string datetime, string period)`
Returns a copy of this datetime with the specified amount added. The `datetime` parameter is in the ISO-8601 format.
The `period` is a string in the ISO-8601 period format.

*Example*

------------------------
DS.ZonedDateTime.offset("2019-07-22T21:00:00Z", "P1Y1D")
------------------------
.Result:
------------------------
2020-07-23T21:00:00Z
------------------------

### `format(string datetime, string inputFormat, string outputFormat)`
Reformats a zoned date-time string.

*Example*

------------------------
DS.ZonedDateTime.format("2019-07-04T21:00:00Z", "yyyy-MM-dd'T'HH:mm:ssVV", "d MMM uuuu")
------------------------
.Result:
------------------------
4 Jul 2019
------------------------

### `compare(string datetime1, string format1, string datetime2, string format2)`
Returns 1 if datetime1 > datetime2, -1 if datetime1 < datetime2, 0 if datetime1 == datetime2.

### `changeTimeZone(string datetime, string format, string timezone)`
Changes the date timezone, retaining the instant. This normally results in a change to the local date-time.
The response is formatted using the same format as an input.

*Example*

------------------------
DS.ZonedDateTime.changeTimeZone("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ", "America/Los_Angeles")
------------------------
.Result:
------------------------
2019-07-04T19:00:00-0700
------------------------

### `toLocalDate(string datetime, string format)`
Returns only local date part of the `datetime` parameter in the ISO-8601 format without the offset.

*Example*

------------------------
DS.ZonedDateTime.toLocalDate("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ")
------------------------
.Result:
------------------------
2019-07-04
------------------------

### `toLocalTime(string datetime, string format)`
Returns only local time part of the `datetime` parameter in the ISO-8601 format without the offset.

*Example*

------------------------
DS.ZonedDateTime.toLocalTime("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ")
------------------------
.Result:
------------------------
21:00:00
------------------------

### `toLocalDateTime(string datetime, string format)`
Returns local datetime part of the `datetime` parameter in the ISO-8601 format without the offset.

*Example*

------------------------
DS.ZonedDateTime.toLocalDateTime("2019-07-04T21:00:00-0500", "yyyy-MM-dd'T'HH:mm:ssZ")
------------------------
.Result:
------------------------
2019-07-04T21:00:00
------------------------

## LocalDateTime

### `now()`
Returns the current date/time from the system UTC clock in ISO-8601 format without a time zone.

*Example*

------------------------
{
    currentLocalTime: DS.LocalDateTime.now()
}
------------------------

.Result:
------------------------
{
    "currentLocalTime": "2019-08-19T18:58:38.313"
}
------------------------

### `offset(string datetime, string period)`
Returns a copy of this datetime with the specified amount added. The `datetime` parameter is in the ISO-8601 format without an offset.
The `period` is a string in the ISO-8601 period format.

*Example*

------------------------
DS.LocalDateTime.offset("2019-07-22T21:00:00", "P1Y1D")
------------------------
.Result:
------------------------
2020-07-23T21:00:00
------------------------

### `format(string datetime, string inputFormat, string outputFormat)`
Reformats a local date-time string.

*Example*

------------------------
DS.LocalDateTime.format("2019-07-04T21:00:00", "yyyy-MM-dd'T'HH:mm:ss", "d MMM uuuu")
------------------------
.Result:
------------------------
4 Jul 2019
------------------------

### `compare(string datetime1, string format1, string datetime2, string format2)`
Returns `1` if `datetime1 > datetime2`, `-1` if `datetime1 < datetime2`, and `0` if `datetime1 == datetime2`.
The `format1` and `format2` parameters must not have an offset or time zone.

*Example*

------------------------
DS.LocalDateTime.compare("2019-07-04T21:00:00", "yyyy-MM-dd'T'HH:mm:ss", "2019-07-04T21:00:00", "yyyy-MM-dd'T'HH:mm:ss")
------------------------
.Result:
------------------------
0
------------------------

## JsonPath

### `select(object json, string path)`

Evaluates JsonPath expression and returns the resulting JSON object.
It uses the https://github.com/json-path/JsonPath[Jayway JsonPath implementation] and fully supports https://goessner.net/articles/JsonPath/[JsonPath specification].

*Example*

.Payload
------------------------
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
------------------------
.DataSonnet map:
------------------------
{
    author: DS.JsonPath.select(payload, "$..book[-2:]..author")[0]
}
------------------------
.Result
------------------------
{
    "author": "Herman Melville"
}
------------------------

## Util

### `select(object obj, string path)`

Returns a value inside the object by given path separated by dot ('.').

*Example*

.Payload
----------
{
  "name": "Foo",
  "language": {
      "name": "Java",
      "version": "1.8"
  }
}
----------
.DataSonnet map:
------------------------
{
  language: DS.Util.select(payload, 'language.name')
}
------------------------
.Result
------------------------
{
   "language": "Java"
}
------------------------

### `filterEx(array objects, string key, string value, function filter_func=function(value1, value2) value1 == value2)`

Filters array of objects by given condition.

*Example*

.Payload
----------
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
----------
.DataSonnet map:
------------------------
{
  nonJavaLanguages: DS.Util.filterEx(payload.languages, 'language', 'Java', function(x, y) x != y)
}
------------------------
.Result
------------------------
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
------------------------

### `groupBy(array arr, string keyName)`
Partitions an array into a Object that contains Arrays, according to the discriminator key you define.
The discriminator can be a path inside the objects to group, e.g. 'language.name'

*Example*

.Payload
----------
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
----------
.DataSonnet map:
------------------------
{
  languageGroups: DS.Util.groupBy(payload.languages, 'language.name')
}
------------------------
.Result
------------------------
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
------------------------

### `remove(object obj, string keyName)`
Removes a property with given name from the object and returns the remaining object

*Example*

.Payload
----------
{
   "availableSeats": 45,
   "airlineName": "Delta",
   "aircraftBrand": "Boeing",
   "aircraftType": "717",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
----------
.DataSonnet map:
------------------------
DS.Util.remove(payload, 'availableSeats')
------------------------
.Result
------------------------
{
   "airlineName": "Delta",
   "aircraftBrand": "Boeing",
   "aircraftType": "717",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
------------------------

### `removeAll(object obj, array keyNames)`
Removes all properties with names from a provided list of strings from the object and returns the remaining object

*Example*

.Payload
----------
{
   "availableSeats": 45,
   "airlineName": "Delta",
   "aircraftBrand": "Boeing",
   "aircraftType": "717",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
----------
.DataSonnet map:
------------------------
DS.Util.removeAll(payload, ['availableSeats', 'aircraftType', 'aircraftBrand'])
------------------------
.Result
------------------------
{
   "airlineName": "Delta",
   "departureDate": "01/20/2019",
   "origin": "PHX",
   "destination": "SEA"
 }
------------------------

### `deepFlattenArrays(array arr)`
Flattens multiple nested arrays into a single array.

*Example*

.Payload
----------
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
----------
.DataSonnet map:
------------------------
DS.Util.flattenArrays(payload)
------------------------
.Result
------------------------
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
------------------------

### `reverse(array arr)`
Returns an array with elements in reverse order.

*Example*

.Payload
----------
[
    "a",
    "b",
    "c",
    "d"
]
----------
.DataSonnet map:
------------------------
DS.Util.reverse(payload)
------------------------
.Result
------------------------
[
    "d",
    "c",
    "b",
    "a",
]
------------------------

### `parseDouble(string str)`

Parses a string which contains a double number and returns its numeric representation

*Example*

.Payload
----------
{
    "numberAsString": "123.45679"
}

----------
.DataSonnet map:
------------------------
{
    num: DS.Util.parseDouble(payload.numberAsString)
}
------------------------
.Result
------------------------
{
    "num": 123.45679
}
------------------------

### `duplicates(array arr, function keyF=id, boolean set=true)`
Returns an array containing duplicate elements from input array. An optional key function returns a value which will be used as a comparison key. If `set` parameter is set to true, only the first duplicate value will be included.

*Example*

.Payload
----------
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
----------
.DataSonnet map:
------------------------
DS.Util.duplicates(payload, function(x) x.language.name)
------------------------
.Result
------------------------
[
  {
    "language": {
      "name": "Java8",
      "version":"1.8.0"
    }
  }
]
------------------------

### `sum(array arr)`
Returns sum of all elements in the array.

*Example*

.Payload
----------
[ 10, 20, 30 ]
----------
.DataSonnet map:
------------------------
DS.Util.sum(payload)
------------------------
.Result
------------------------
60
------------------------

### `round(double num, int precision)`
Rounds a double to the number of digits after the decimal point

*Example*

.Payload
----------
{
    "num": 123.562567558
}
----------
.DataSonnet map:
------------------------
DS.Util.round(payload.num, 6)
------------------------
.Result
------------------------
123.562568
------------------------

### `counts(array arr, function keyF=id)`
Returns an object where keys are the results of calling keyF on the values, and the values are the counts of values that produced the corresponding key.

*Example*

.Payload
----------
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
----------
.DataSonnet map:
------------------------
DS.Util.counts(payload, function(x) x.language.name);
------------------------
.Result
------------------------
{
    "Java": 2,
    "Scala": 1
}
------------------------

### `mapToObject(arr, keyF, valueF=id)`
Maps an array into an object, where the keys are the result of calling keyF on each value (which becomes the value at the key). If valueF is provided it gets run on the value. Duplicate keys are removed.

*Example*

.Payload
----------
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

----------
.DataSonnet map:
------------------------
DS.Util.mapToObject(payload, function(x) x.language.name, function(v) v.language);
------------------------
.Result
------------------------
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
------------------------

## Regex

### `regexFullMatch(string pattern, string input)`
Matches the entire input against the pattern (anchored start and end). If there's no match, returns `null`. If there's a match, returns a JSON object which has the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

*Example*

.DataSonnet map:
------------------------
DS.Regex.regexFullMatch(@'h(?P<mid>.*)o', 'hello');
------------------------
.Result
------------------------
{
  "captures": [
    "ell"
  ],
  "namedCaptures": {
    "mid": "ell"
  },
  "string": "hello"
}
------------------------

### `regexPartialMatch(string pattern, string input)`
Matches the input against the pattern (unanchored). If there's no match, returns `null`. If there's a match, returns a JSON object which has the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

*Example*

.DataSonnet map:
------------------------
DS.Regex.regexPartialMatch(@'e(?P<mid>.*)o', 'hello')
------------------------
.Result
------------------------
{
  "captures": [
    "ll"
  ],
  "namedCaptures": {
    "mid": "ll"
  },
  "string": "ello"
}
------------------------

### `regexScan(string pattern, string input)`
Finds all matches of the input against the pattern. If there are any matches, returns an array of JSON objects which have the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

*Example*

.DataSonnet map:
------------------------
DS.Regex.regexScan(@'(?P<user>[a-z]*)@(?P<domain>[a-z]*).org', 'modus@datasonnet.org,box@datasonnet.org')
------------------------
.Result
------------------------
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
------------------------

### `regexQuoteMeta(string str)`
Returns a literal pattern string for the specified string.

*Example*

.DataSonnet map:
------------------------
DS.Regex.regexQuoteMeta(@'1.5-2.0?')
------------------------
.Result
------------------------
"1\\.5-2\\.0\\?"
------------------------

### `regexReplace(string str, string pattern, string replacement)`
Returns the input with the first match replaced by `replacement` string.

*Example*

.DataSonnet map:
------------------------
DS.Regex.regexReplace('wishyfishyisishy', @'ish', 'and')
------------------------
.Result
------------------------
"wandyfishyisishy"
------------------------

### `regexGlobalReplace(string str, string pattern, string replacement)`
Returns the input with all matches replaced by `replacement` string.

*Example*

.DataSonnet map:
------------------------
DS.Regex.regexGlobalReplace('wishyfishyisishy', @'ish', 'and')
------------------------
.Result
------------------------
"wandyfandyisandy"
------------------------

### `regexGlobalReplace(string str, string pattern, function replacement)`
Returns the input with all matches replaced by the result of the `replacement` function. The function must return string result and take a single object as an argument in the following structure:

- `string` - the matched string;
- `captures` - array of captured subgroups in the match, if any;
- `namedCaptures` - map of named subgroups, if any;

*Example*

.DataSonnet map:
------------------------
local square(obj) = std.toString(std.pow(std.parseInt(obj.string), 2));

DS.Regex.regexGlobalReplace("xxx2yyy4zzz6aaa", "\\d", square)
------------------------
.Result
------------------------
"xxx4yyy16zzz36aaa"
------------------------

## URL

### `encode(string data, string encoding="UTF-8")`

Translates a string into `application/x-www-form-urlencoded` format using the supplied encoding scheme to obtain the bytes for unsafe characters. The default encoding is `UTF-8`.

*Example*

.DataSonnet map:
------------------------
DS.URL.encode('Hello World')
------------------------
.Result
------------------------
"Hello+World"
------------------------

### `decode(string data, string encoding="UTF-8")`

Decodes a application/x-www-form-urlencoded string using a specific encoding scheme. The supplied encoding is used to determine what characters are represented by any consecutive sequences of the form "%xy".

*Example*

.DataSonnet map:
------------------------
DS.URL.decode('Hello+World')
------------------------
.Result
------------------------
"Hello World"
------------------------
## DW