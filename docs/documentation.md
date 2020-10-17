# std

Standard Library
This page describes the functions available in Jsonnet's standard library, i.e. the object implicitly bound to the std variable. Some of the standard library functions can be implemented in Jsonnet. Their code can be found in the std.jsonnet file. The behavior of some of the other functions, i.e. the ones that expose extra functionality not otherwise available to programmers, is described formally in the specification.

The standard library is implicitly added to all Jsonnet programs by enclosing them in a local construct. For example, if the program given by the user is {x: "foo"}, then the actual code executed would be local std = { ... }; {x: "foo"}. The functions in the standard library are all hidden fields of the std object.

---
## External Variables
### `extVar(x)`
If an external variable with the given name was defined, return its string value. Otherwise, raise an error.

---
## Types and Reflection
### `thisFile():string`
Note that this is a field. It contains the current Jsonnet filename as a string.

Example:
```
.thisFile
```
Output:
```json5
"example.ds"
```

---
### `type(x:any):string`
Return a string that indicates the type of the value. The possible return values are: "array", "boolean", "function", "null", "number", "object", and "string".

The following functions are also available and return a boolean: std.isArray(v), std.isBoolean(v), std.isFunction(v), std.isNumber(v), std.isObject(v), and std.isString(v).

Example:
```json5
{
    array: std.type([]),
    boolean: std.type(true),
    "function": std.type(function(x) x),
    "null": std.type(null),
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
### `length(x:any):number`
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
### `objectHas(o:object, f:string):boolean`
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
### `objectFields(o:object):array`
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
### `objectHasAll(o:object, f:string):boolean`
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
### `objectFieldsAll(o:object):array`
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
### `prune(a:array):array`
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
### `mapWithKey(func:function, obj:object):object`
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
### `abs(n:number):number`

Example:
```json5
std.abs(-1)
```
Output:
```json5
1
```

---
### `sign(n)`

Example:
```json5
std.sign(1) // error
```
Output:
```json5

```

---
### `max(a:number, b:number):number`

Example:
```json5
std.max(5,1)
```
Output:
```json5
5
```

---
### `min(a:number, b:number):number`

Example:
```json5
std.min(5,1)
```
Output:
```json5
1
```

---
### `pow(x:number, exp:number):number`

Example:
```json5
std.pow(5,2)
```
Output:
```json5
25
```

---
### `exp(x:number):number`
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
### `log(x:number):number`

Example:
```json5
std.log(2)
```
Output:
```json5
0.6931471805599453
```

---
### `exponent(x:number):number`

Example:
```json5
std.exponent(10)
```
Output:
```json5
4
```

---
### `mantissa(x:number):number`

Example:
```json5
std.mantissa(10)
```
Output:
```json5
0.625
```

---
### `floor(x:number):number`

Example:
```json5
std.floor(1.9)
```
Output:
```json5
1
```

---
### `ceil(x:number):number`

Example:
```json5
std.ceil(1.9)
```
Output:
```json5
2
```

---
### `sqrt(x:number):number`

Example:
```json5
std.sqrt(4)
```
Output:
```json5
2
```

---
### `sin(x:number):number`

Example:
```json5
std.sin(1)
```
Output:
```json5
0.8414709848078965
```

---
### `cos(x:number):number`

Example:
```json5
std.cos(1)
```
Output:
```json5
0.5403023058681398
```

---
### `tan(x:number):number`

Example:
```json5
std.tan(1)
```
Output:
```json5
1.5574077246549023
```

---
### `asin(x:number):number`

Example:
```json5
std.asin(1)
```
Output:
```json5
1.5707963267948966
```

---
### `acos(x:number):number`

Example:
```json5
std.acos(1)
```
Output:
```json5
0
```

---
### `atan(x:number):number`


Example:
```json5
std.atan(1)
```
Output:
```json5
0.7853981633974483
```
---
## Assertions and Debugging
### `assertEqual(a:any, b:any):boolean`
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
### `toString(any:any):string`
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
### `codepoint(str:string):number`
Returns the positive integer representing the unicode codepoint of the character in the given single-character string. This function is the inverse of std.char(n).

Example:
```json5
std.codepoint("k")
```
Output:
```json5
107
```
---
### `char(n:number):string`
Returns a string of length one whose only unicode codepoint has integer id n. This function is the inverse of std.codepoint(str).

Example:
```json5
std.char(107)
```
Output:
```json5
"k"
```
---
### `substr(str:string, from:number, len:number):string`
Returns a string that is the part of s that starts at offset from and is len codepoints long. If the string s is shorter than from+len, the suffix starting at position from will be returned.

Example:
```json5
std.substr("Hello World!", 0,5)
```
Output:
```json5
"Hello"
```
---
### `findSubstr(pat:string, str:string):array`
Returns an array that contains the indexes of all occurances of pat in str.

Example:
```json5
std.findSubstr("o","Hello World!")
```
Output:
```json5
[4,7]
```
---
### `startsWith(a:string, b:string):boolean`
Returns whether the string a is prefixed by the string b.

Example:
```json5
std.startsWith("Hello World","Hello ")
```
Output:
```json5
true
```
---
### `endsWith(a:string, b:string):boolean`
Returns whether the string a is suffixed by the string b.

Example:
```json5
std.endsWith("Hello World","World")
```
Output:
```json5
true
```
---
### `split(str:string, c:string):array`
Split the string str into an array of strings, divided by the single character c.

Example:
```json5
std.split("foo/bar", "/")
```
Output:
```json5
["foo", "bar"]
```
---
### `splitLimit(str:string, c:string, maxsplits:number):array`
As std.split(str, c) but will stop after maxsplits splits, thereby the largest array it will return has length maxsplits + 1. A limit of -1 means unlimited.

Example:
```json5
std.splitLimit("foo/bar", "/", 1)
```
Output:
```json5
["foo", "bar"]
```
---
### `strReplace(str:string, from:number, to:number):string`
Returns a copy of the string in which all occurrences of string from have been replaced with string to

Example:
```json5
std.strReplace('I like to skate with my skateboard', 'skate', 'surf')
```
Output:
```json5
"I like to surf with my surfboard"
```
---
### `asciiUpper(str:string):string`
Returns a copy of the string in which all ASCII letters are capitalized.

Example:
```json5
std.asciiUpper('100 Cats!')
```
Output:
```json5
"100 CATS!"
```
---
### `asciiLower(str:string):string`
Returns a copy of the string in which all ASCII letters are lower cased.

Example:
```json5
std.asciiLower('100 Cats!')
```
Output:
```json5
"100 cats!"
```
---
### `stringChars(str:string):array`
Split the string str into an array of strings, each containing a single codepoint.

Example:
```json5
std.stringChars("foo")
```
Output:
```json5
["f", "o", "o"]
```
---
### `format(str:string, vals:any):string`
Format the string str using the values in vals. The values can be an array, an object, or in other cases are treated as if they were provided in a singleton array. The string formatting follows the same rules as Python. The % operator can be used as a shorthand for this function.

Example:
```json5
std.format("Hello %03d", 12)
```
Output:
```json5
"Hello 012"
```
---
### `escapeStringBash(str:string):string`
Wrap str in single quotes, and escape any single quotes within str by changing them to a sequence '"'"'. This allows injection of arbitrary strings as arguments of commands in bash scripts.

Example:
```json5
std.escapeStringBash("escaped string")
```
Output:
```json5
"'escaped string'"
```
---
### `escapeStringDollars(str:string):string`
Convert $ to $$ in str. This allows injection of arbitrary strings into systems that use $ for string interpolation (like Terraform).

Example:
```json5
std.escapeStringDollars("$")
```
Output:
```json5
"$$"
```
---
### `escapeStringJson(str:string):string`
Convert str to allow it to be embedded in a JSON representation, within a string. This adds quotes, escapes backslashes, and escapes unprintable characters.

Example:
```json5
{ local description = "Multiline\nc:\\path", json: "{name: %s}" % std.escapeStringJson(description) }
```
Output:
```json5
{"json": "{name: \"Multiline\\nc:\\\\path\"}"}
```
---
## Parsing
### `parseInt(str:string):number`
Parses a signed decimal integer from the input string.

Example:
```json5
std.parseInt("123")
```
Output:
```json5
123
```
---
### `parseOctal(str:string):number`
Parses an unsigned octal integer from the input string. Initial zeroes are tolerated.

Example: 
```json5
std.parseOctal("755") 
```
Output:
```json5
493
```

---
### `parseHex(str:string):number`
Parses an unsigned hexadecimal integer, from the input string. Case insensitive.

Example: 
```json5
std.parseHex("ff")
```
Output:
```json5
255
```

---
### `parseJson(str:string):any`
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
## Manifestation
### `manifestIni(ini:object)`
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
### `manifestPython(v:object)`
Convert the given value to a JSON-like form that is compatible with Python. The chief differences are True / False / None instead of true / false / null.

Example:
```json5
std.manifestPython({
  b: ["foo", "bar"],
  c: true,
  d: null,
  e: { f1: false, f2: 42 },
})
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
### `manifestPythonVars(conf:object)`
Convert the given object to a JSON-like form that is compatible with Python. The key difference to std.manifestPython is that the top level is represented as a list of Python global variables.

Example:
```json5
std.manifestPythonVars({
  b: ["foo", "bar"],
  c: true,
  d: null,
  e: { f1: false, f2: 42 },
})
```
Output:
```json5
b = ["foo", "bar"]
c = True
d = None
e = {"f1": False, "f2": 42}
```
---
### `manifestJsonEx(value:object, indent:string):object`
Convert the given object to a JSON form. indent is a string containing one or more whitespaces that are used for indentation:

Example:
```json5
std.manifestJsonEx(
{
    x: [1, 2, 3, true, false, null,
        "string\nstring"],
    y: { a: 1, b: 2, c: [1, 2] },
}, "    ")          
```
Output:
```json5
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
```
---
### `manifestYamlDoc(value:object)`
Convert the given value to a YAML form. Note that std.manifestJson could also be used for this purpose, because any JSON is also valid YAML. But this function will produce more canonical-looking YAML.

Example:
```json5
std.manifestYamlDoc(
  {
    x: [1, 2, 3, true, false, null,
        "string\nstring\n"],
    y: { a: 1, b: 2, c: [1, 2] },
  },
  indent_array_in_object=false)
```
Output:
```yaml
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
```
---
### `manifestYamlStream(value)`
Given an array of values, emit a YAML "stream", which is a sequence of documents separated by --- and ending with ....

Example:
```json5
std.manifestYamlStream(
  ['a', 1, []],
  indent_array_in_object=false,)
```
Output:
```json5
---
"a"
---
1
---
[]
...
```
---
### `manifestXmlJsonml(value)`
Convert the given JsonML-encoded value to a string containing the XML.

Example:
```json5
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
```
Output:
```svg
<svg height="100" width="100">
  <circle cx="50" cy="50" fill="red" r="40"
  stroke="black" stroke-width="3"></circle>
</svg>
```
---
## Arrays
### `makeArray(sz:number, func:function):array`
Create a new array of sz elements by calling func(i) to initialize each element. Func is expected to be a function that takes a single parameter, the index of the element it should initialize.

Example:
```json5
std.makeArray(3,function(x) x * x)
```
Output:
```json5
[0, 1, 4]
```
---
### `count(arr:array, x:any):number`
Return the number of times that x occurs in arr.

Example:
```json5
std.count([1,5,2,3,1], 1)
```
Output:
```json5
2
```
---
### `find(value:any, arr:array):array`
Returns an array that contains the indexes of all occurances of value in arr.

Example:
```json5
std.find(1, [1,2,3,1])
```
Output:
```json5
[0,3]
```
---
### `map(func:function, arr:array):array`
Apply the given function to every element of the array to form a new array.

Example:
```json5
std.map(function(it) it>2, [1,2,3,4])
```
Output:
```json5
[
  false,
  false,
  true,
  true
]
```
---
### `mapWithIndex(func:function, arr:array):array`
Similar to map above, but it also passes to the function the element's index in the array. The function func is expected to take the index as the first parameter and the element as the second.

Example:
```json5
std.mapWithIndex(function(index,item) item < index, [4,3,2,1,0])
```
Output:
```json5
[
  false,
  false,
  false,
  true,
  true
]
```
---
### `filterMap(filter_func:function, map_func:function, arr:array):array`
This is primarily used to desugar array comprehension syntax. It first filters, then maps the given array, using the two functions provided.

Example:
```json5
std.filterMap(
    function(item) item>2,
    function(item) item,
    [1,2,3,4,5]
)
```
Output:
```json5
[
  3,
  4,
  5
]
```
---
### `flatMap(func:function, arr:array):array`
Apply the given function to every element of the array to form a new array then flatten the result. It can be thought of as a generalized map, where each element can get mapped to 0, 1 or more elements.

Example:
```json5
std.flatMap(function(x) [x, x], [1, 2, 3])
```
Output:
```json5
[1, 1, 2, 2, 3, 3]
```
---
### `filter(func:function, arr:array):array`
Return a new array containing all the elements of arr for which the func function returns true.

Example:
```json5
std.filter(
    function(item) item>2,
    [1,2,3,4,5]
)
```
Output:
```json5
[
  3,
  4,
  5
]
```
---
### `foldl(func:function, arr:array, init:any):any`
Classic foldl function. Calls the function on the result of the previous function call and each array element, or init in the case of the initial element. Traverses the array from left to right.

Example:
```json5
std.foldl(
    function(acc,item) acc+""+item,
    [1,2,3,4],
    0
)
```
Output:
```json5
"01234"
```
---
### `foldr(func:function, arr:array, init:any):any`
Classic foldl function. Calls the function on each array element and the result of the previous function call, or init in the case of the initial element. Traverses the array from right to left.

Example:
```json5
std.foldr(
    function(acc,item) acc+""+item,
    [1,2,3,4],
    0
)
```
Output:
```json5
"12340"
```
---
### `range(from:number, to:number):array`
Return an array of ascending numbers between the two limits, inclusively.

Example:
```json5
std.range(0,5)
```
Output:
```json5
[0,1,2,3,4,5]
```
---
### `join(sep:string, arr:array):any`
If sep is a string, then arr must be an array of strings, in which case they are concatenated with sep used as a delimiter. If sep is an array, then arr must be an array of arrays, in which case the arrays are concatenated in the same way, to produce a single array.

Example:
```json5
std.join(".", ["www", "google", "com"])
```
Output:
```json5
"www.google.com"
```
---
### `lines(arr:array):string`
Concatenate an array of strings into a text file with newline characters after each string. This is suitable for constructing bash scripts and the like.

Example:
```json5
std.lines(["a","b"])
```
Output:
```json5
"a\nb\n"
```
---
### `flattenArrays(arrs:array):array`
Concatenate an array of arrays into a single array.

Example:
```json5
std.flattenArrays([[1, 2], [3, 4], [[5, 6], [7, 8]]])
```
Output:
```json5
[1, 2, 3, 4, [5, 6], [7, 8]]
```
---
### `sort(arr:array, keyF=id):array`
Sorts the array using the <= operator.

Optional argument keyF is a single argument function used to extract comparison key from each array element. Default value is identity function keyF=function(x) x.

Example:
```json5
std.sort([3,1,2,0])
```
Output:
```json5
[0,1,2,3]
```
---
### `uniq(arr:array, keyF=id):array`
Removes successive duplicates. When given a sorted array, removes all duplicates.

Optional argument keyF is a single argument function used to extract comparison key from each array element. Default value is identity function keyF=function(x) x.

Example:
```json5
std.uniq([0,1,1,2,2,3,3])
```
Output:
```json5
[0,1,2,3]
```
---
## Sets
Sets are represented as ordered arrays without duplicates.

Note that the std.set* functions rely on the uniqueness and ordering on arrays passed to them to work. This can be guarenteed by using std.set(arr). If that is not the case, the functions will quietly return non-meaningful results.

All set.set* functions accept keyF function of one argument, which can be used to extract key to use from each element. All Set operations then use extracted key for the purpose of identifying uniqueness. Default value is identity function local id = function(x) x

---
### `set(arr:array, keyF=id):array`
Shortcut for std.uniq(std.sort(arr)).

Example:
```json5
std.set([0,1,1,2,2,3,3])
```
Output:
```json5
[0,1,2,3]
```
---
### `setInter(a:array, b:array, keyF=id):array`
Set intersection operation (values in both a and b).

Example:
```json5
std.setInter([1],[2,1])
```
Output:
```json5
[1]
```
---
### `setUnion(a:array, b:array, keyF=id):array`
Set union operation (values in any of a or b). Note that + on sets will simply concatenate the arrays, possibly forming an array that is not a set (due to not being ordered without duplicates).

Example:
```json5
std.setUnion([1, 2], [2, 3])
```
Output:
```json5
[1, 2, 3]
```
---
### `setDiff(a:array, b:array, keyF=id):array`
Set difference operation (values in a but not b).

Example:
```json5
std.setUnion([1],[2])
```
Output:
```json5
[1,2]
```
---
### `setMember(x:any, arr:array, keyF=id):boolean`
Returns true if x is a member of array, otherwise false.

Example:
```json5
std.setMember(0,[0,1,1,2,2,3,3])
```
Output:
```json5
true
```
---
## Encoding
### `base64(input:string):string`
Encodes the given value into a base64 string. The encoding sequence is A-Za-z0-9+/ with = to pad the output to a multiple of 4 characters. The value can be a string or an array of numbers, but the codepoints / numbers must be in the 0 to 255 range. The resulting string has no line breaks.

Example:
```json5
std.base64("hello")
```
Output:
```json5
"aGVsbG8="
```
---
### `base64DecodeBytes(str:string):array`
Decodes the given base64 string into an array of bytes (number values). Currently assumes the input string has no linebreaks and is padded to a multiple of 4 (with the = character). In other words, it consumes the output of std.base64().

Example:
```json5
std.base64DecodeBytes("aGVsbG8=")
```
Output:
```json5
[
  104,
  101,
  108,
  108,
  111
]
```
---
### `base64Decode(str:string):string`
Deprecated, use std.base64DecodeBytes and decode the string explicitly (e.g. with std.decodeUTF8) instead.
Behaves like std.base64DecodeBytes() except returns a naively encoded string instead of an array of bytes.

Example:
```json5
std.base64Decode("aGVsbG8=")
```
Output:
```json5
"hello"
```
---
### `md5(s:string):string`
Encodes the given value into an MD5 string.

Example:
```json5
std.md5("hello")
```
Output:
```json5
"5d41402abc4b2a76b9719d911017c592"
```
---
## JSON Merge Patch
### `mergePatch(target:any, patch:any):any`
Applies patch to target according to RFC7396

Example:
```json5
std.mergePatch({b:2}, {a:1})
```
Output:
```json5
{
  "a": 1,
  "b": 2
}
```
---
## Debugging
### `trace(str:string, rest:amy):any`
Available since version 0.11.0.
Outputs the given string str to stderr and returns rest as the result.

Example:
```
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
Output:
```json5
TRACE: test.jsonnet:3 cond is true returning {"b": true}
{
   "a": {
      "b": true
   }
}
```
---
# DS
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
### `now():zoneddate`
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
### `offset(datetime:string, period:string):zoneddate`
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
### `format(datetime:string, inputFormat:string, outputFormat:string):zoneddate`
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
### `now():localdate`
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
### `offset(datetime:string, period:string):localdate`
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
### `format(datetime:string, inputFormat:string, outputFormat:string):localdate`
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
### `sum(array:array):number`
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