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
## Crypto
### `HMACBinary(secret:String, content:String, algorithm:String):String`
Computes an HMAC hash (with a secret cryptographic key) on input content.

Example:
```json5
DW.Crypto.HMACBinary("key_re_loca", "xxxx")
```
Output:
```json5
".-\ufffd\ufffd\u0012\ufffdۊ\ufffd\ufffd\u0000\ufffd\u0012\u0018R\ufffd\ufffd=\ufffd*"
```
---
### `HMACWith(secret:String, content:String, algorithm:String):String`
Computes an HMAC hash (with a secret cryptographic key) on input content, then transforms the result into a lowercase, hexadecimal string.

Example:
```json5
DW.Crypto.HMACWith("secret_key", "Some Value to Hash", "HmacSHA256")
```
Output:
```json5
"b51b4fe8c4e37304605753272b5b4321f9644a9b09cb1179d7016c25041d1747"
```
---
### `MD5(content:String):String`
Computes the MD5 hash and transforms the binary result into a hexadecimal lower case string.

Example:
```json5
DW.Crypto.MD5("asd")
```
Output:
```json5
"7815696ecbf1c96e6894b779456d330e"
```
---
### `SHA1(content:String):String`
Computes the SHA1 hash and transforms the result into a hexadecimal, lowercase string.

Example:
```json5
DW.Crypto.SHA1("dsasd")
```
Output:
```json5
"2fa183839c954e6366c206367c9be5864e4f4a65"
```
---
### `hashWith(content:String, algorithm:String):String`
Computes the hash value of binary content using a specified algorithm.

The first argument specifies the binary content to use to calculate the hash value, and the second argument specifies the hashing algorithm to use. The second argument must be any of the accepted Algorithm names:

| Algorithm Names | Description |
|-----------------|-------------|
| MD2 | The MD2 message digest algorithm as defined in RFC 1319. |
| MD5 | The MD5 message digest algorithm as defined in RFC 1321. |
| SHA-1, SHA-256, SHA-384, SHA-512 | Hash algorithms defined in the FIPS PUB 180-2. SHA-256 is a 256-bit hash function intended to provide 128 bits of security against collision attacks, while SHA-512 is a 512-bit hash function intended to provide 256 bits of security. A 384-bit hash may be obtained by truncating the SHA-512 output. |

Example:
```json5
DW.Crypto.hashWith("hello", "MD2")
```
Output:
```json5
"\ufffd\u0004ls\ufffd\u00031\ufffdh\ufffd}8\u0004\ufffd\u0006U"
```
---
## Arrays
This module contains helper functions for working with arrays.
### `countBy(array:Array, funct(item):Function):Number`
Counts the elements in an array that return true when the matching function is applied to the value of each element.


Example:
```json5
DW.Arrays.countBy([1, 2, 3, 4], function(it) (it % 2) == 0)
```
Output:
```json5
2
```
---
### `divideBy(array:Array, size:Number): Array`
Breaks up an array into sub-arrays that contain the specified number of elements.

When there are fewer elements in the input array than the specified number, the function fills the sub-array with those elements. When there are more elements, the function fills as many sub-arrays needed with the extra elements.

Example:
```json5
{
  "divideBy" : [
      { "divideBy2" : DW.Arrays.divideBy([1, 2, 3, 4, 5],2) },
      { "divideBy2" : DW.Arrays.divideBy([1, 2, 3, 4, 5, 6],2) },
      { "divideBy3" : DW.Arrays.divideBy([1, 2, 3, 4, 5],3) }
  ]
}
```
Output:
```json5
{
 "divideBy": [
  {
    "divideBy2": [
      [ 1, 2 ],
      [ 3, 4 ],
      [ 5 ]
    ]
  },
  {
    "divideBy2": [
      [ 1, 2 ],
      [ 3, 4 ],
      [ 5, 6 ]
    ]
  },
    {
      "divideBy3": [
        [ 1, 2, 3 ],
        [ 4, 5 ]
      ]
    }
 ]
}
```
---
### `drop(array:Array, index:Number):Array`
Drops the first n elements. It returns the original array when n <= 0 and an empty array when n > sizeOf(array).

Example:
```json5
local users=["Marcus", "Jake", "Julian"];
DW.Arrays.drop(users, 2)
```
Output:
```json5
[
  "Julian"
]
```
---
### `dropWhile(array:Array, function(item):Function):Array`
Drops elements from the array while the condition is met but stops the selection process when it reaches an element that fails to satisfy the condition.

Example:
```json5
local arr=[1,2,3,2,1];
dropWhile(arr, function(it) it < 3)
```
Output:
```json5
[3,2,1]
```
---
### `every(array:Array, function(item):Function): Boolean`
Returns true if every element in the array matches the condition.

The function stops iterating after the first negative evaluation of an element in the array.

Example:
```json5
{ "results" : [
    DW.Arrays.every([1,1,1], function(it) it == 1),
    DW.Arrays.every([1,2,1], function(it) it == 1)
   ]
}
```
Output:
```json5
{
   "results": [true, false]
}
```
---
### `firstWith(array:Array, funct(item,index):Function):Any`
Returns the first element that satisfies the condition, or returns null if no element meets the condition.

Example:
```json5
local users = [{name: "Mariano", lastName: "Achaval"}, {name: "Ana", lastName: "Felisatti"}, {name: "Mariano", lastName: "de Sousa"}];
{
  a: DW.Arrays.firstWith(users, function(user, index) user.name == "Mariano"),
  b: DW.Arrays.firstWith(users, function(user, index) user.name == "Peter")
}
```
Output:
```json5
{
  "a": {
    "name": "Mariano",
    "lastName": "Achaval"
  },
  "b": null
}
```
---
### `indexOf(array:Array, Any):Number`
Returns the index of the first occurrence of an element within the array.

Example:
```json5
local users = ["Mariano", "Leandro", "Julian"];
DW.Arrays.indexOf(users, "Julian")
```
Output:
```json5
2
```
---
### `indexWhere(array:Array, function(item):Function):Number`
Returns the index of the first occurrence of an element that matches a condition within the array.

Example:
```json5
local users = ["Mariano", "Leandro", "Julian"];
DW.Arrays.indexWhere(users, function(item) item DW.Core.startsWith "Jul")
```
Output:
```json5
2
```
---
### `join(arr1:Array, arr2:Array, funct(item1):Function, funct(item2):Function):Array`
Joins two arrays of objects by a given ID criteria.

join returns an array all the left items, merged by ID with any right items that exist.

Example:
```json5
local users = [{id: "1", name:"Mariano"},{id: "2", name:"Leandro"},{id: "3", name:"Julian"},{id: "5", name:"Julian"}];
local products = [{ownerId: "1", name:"DataWeave"},{ownerId: "1", name:"BAT"}, {ownerId: "3", name:"DataSense"}, {ownerId: "4", name:"SmartConnectors"}];
DW.Arrays.join(users, products, function(user) user.id, function(product) product.ownerId)
```
Output:
```json5
[
  {
    "l": {
      "id": "1",
      "name": "Mariano"
    },
    "r": {
      "ownerId": "1",
      "name": "DataWeave"
    }
  },
  {
    "l": {
      "id": "1",
      "name": "Mariano"
    },
    "r": {
      "ownerId": "1",
      "name": "BAT"
    }
  },
  {
    "l": {
      "id": "3",
      "name": "Julian"
    },
    "r": {
      "ownerId": "3",
      "name": "DataSense"
    }
  }
]
```
---
### `leftJoin(arr1:Array, arr2:Array, funct(item1):Function, funct(item2):Function):Array`
Joins two arrays of objects by a given ID criteria.

leftJoin returns an array all the left items, merged by ID with any right items that meet the joining criteria.

Example:
```json5
local users = [{id: "1", name:"Mariano"},{id: "2", name:"Leandro"},{id: "3", name:"Julian"},{id: "5", name:"Julian"}];
local products = [{ownerId: "1", name:"DataWeave"},{ownerId: "1", name:"BAT"}, {ownerId: "3", name:"DataSense"}, {ownerId: "4", name:"SmartConnectors"}];
DW.Arrays.leftJoin(users, products, function(user) user.id, function(product) product.ownerId)
```
Output:
```json5
[
  {
    "l": {
      "id": "1",
      "name": "Mariano"
    },
    "r": {
      "ownerId": "1",
      "name": "DataWeave"
    }
  },
  {
    "l": {
      "id": "1",
      "name": "Mariano"
    },
    "r": {
      "ownerId": "1",
      "name": "BAT"
    }
  },
  {
    "l": {
      "id": "2",
      "name": "Leandro"
    }
  },
  {
    "l": {
      "id": "3",
      "name": "Julian"
    },
    "r": {
      "ownerId": "3",
      "name": "DataSense"
    }
  },
  {
    "l": {
      "id": "5",
      "name": "Julian"
    }
  }
]
```
---
### `outerJoin(arr1:Array, arr2:Array, funct(item1):Function, funct(item2):Function):Array`
Joins two array of objects by a given ID criteria.

outerJoin returns an array with all the left items, merged by ID with the right items in cases where any exist, and it returns right items that are not present in the left.

Example:
```json5
local users = [{id: "1", name:"Mariano"},{id: "2", name:"Leandro"},{id: "3", name:"Julian"},{id: "5", name:"Julian"}];
local products = [{ownerId: "1", name:"DataWeave"},{ownerId: "1", name:"BAT"}, {ownerId: "3", name:"DataSense"}, {ownerId: "4", name:"SmartConnectors"}];
outerJoin(users, products, (user) -> user.id, (product) -> product.ownerId)
```
Output:
```json5
[
  {
    "l": {
      "id": "1",
      "name": "Mariano"
    },
    "r": {
      "ownerId": "1",
      "name": "DataWeave"
    }
  },
  {
    "l": {
      "id": "1",
      "name": "Mariano"
    },
    "r": {
      "ownerId": "1",
      "name": "BAT"
    }
  },
  {
    "l": {
      "id": "2",
      "name": "Leandro"
    }
  },
  {
    "l": {
      "id": "3",
      "name": "Julian"
    },
    "r": {
      "ownerId": "3",
      "name": "DataSense"
    }
  },
  {
    "l": {
      "id": "5",
      "name": "Julian"
    }
  },
  {
    "r": {
      "ownerId": "4",
      "name": "SmartConnectors"
    }
  }
]
```
---
### `partition(array:Array, funct(item)):Object`
Separates the array into the elements that satisfy the condition from those that do not.

Example:
```json5
local arr = [0,1,2,3,4,5];
DW.Arrays.partition(arr, function(item) (item % 2) == 0)
```
Output:
```json5
{
  "success": [
    0,
    2,
    4
  ],
  "failure": [
    1,
    3,
    5
  ]
}
```
---
### `slice(array:Array, start:Number, end:Number):Array`
Selects the interval of elements that satisfy the condition: from <= indexOf(array) < until

Example:
```json5
local arr = [0,1,2,3,4,5];
DW.Arrays.slice(arr, 1, 4)
```
Output:
```json5
[1,2,3]
```
---
### `some(array:Array, function(item):Function):Boolean`
Returns true if at least one element in the array matches the specified condition.

The function stops iterating after the first element that matches the condition is found.

Example:
```json5
 { "results" : [
      DW.Arrays.some([1,2,3], function(it) (it % 2) == 0),
      DW.Arrays.some([1,2,3], function(it) it == 100)
   ]
}
```
Output:
```json5
{
   "results": [true,false]
}
```
---
### `splitAt(array:Array, index:Number):Object`
Splits an array into two at a given position.

Example:
```json5
var users = ["Mariano", "Leandro", "Julian"]
DW.Arrays.splitAt(users,1)
```
Output:
```json5
{
  "l": [
    "Mariano"
  ],
  "r": [
    "Leandro",
    "Julian"
  ]
}
```
---
### `splitWhere(array:Array, function(item):Function):Object`
Splits an array into two at the first position where the condition is met.

Example:
```json5
local users = ["Mariano", "Leandro", "Julian", "Tomo"];
DW.Arrays.splitWhere(users, function(item) item DW.Core.startsWith("Jul"))
```
Output:
```json5
{
  "l": [
    "Mariano",
    "Leandro"
  ],
  "r": [
    "Julian",
    "Tomo"
  ]
}
```
---
### `sumBy(array:Array, funct(item):Function):Number`
Returns the sum of the values of the elements in an array.

Example:
```json5
DW.Arrays.sumBy([ { a: 1 }, { a: 2 }, { a: 3 } ], function(item) item.a)
```
Output:
```json5
6
```
---
### `take(array:Array, index:Number):Array`
Selects the first n elements. It returns an empty array when n <= 0 and the original array when n > sizeOf(array).

Example:
```json5
local users = ["Mariano", "Leandro", "Julian"];
DW.Arrays.take(users, 2)
```
Output:
```json5
["Mariano","Leandro"]
```
---
### `takeWhile(array:Array, funct(item):Function):Array`
Selects elements from the array while the condition is met but stops the selection process when it reaches an element that fails to satisfy the condition.

To select all elements that meet the condition, use the filter function.

Example:
```json5
local arr = [0,1,2,1];
DW.Arrays.takeWhile(arr, function(item) item <= 1)
```
Output:
```json5
[0,1]
```
---
## Binaries
This module contains helper functions for working with binaries.
### `fromBase64(str:String):Binary`
Transforms a Base64 string into a binary value.

Example:
```json5
fromBase64(payload)
```
Output:
The output of this function is a binary value that represents the image generated in example toBase64.
---
### `fromHex(str:String):Binary`
Transforms a hexadecimal string into a binary.

Example:
```json5
{ "hexToBinary": fromHex("4D756C65") }
```
Output:
```json5
{ "hexToBinary": "Mule" }
```
---
### `readLinesWith(bin:Binary, str:String): Array`
Splits the specified binary content into lines and returns the results in an array.

Example:
```json5
var content = read("Line 1\nLine 2\nLine 3\nLine 4\nLine 5\n", "application/octet-stream")
{
   lines : readLinesWith("Line 1\nLine 2\nLine 3\nLine 4\nLine 5\n","UTF-8")
}
```
Output:
```json5
{
   "lines": [ "Line 1", "Line 2", "Line 3", "Line 4", "Line 5" ]
}
```
---
### `toBase64(bin:Binary): String`
Transforms a binary value into a Base64 string.

Example:
```json5
var emailChecksum = Crypto::MD5("achaval@gmail.com" as Binary)
var image = readUrl(log("https://www.gravatar.com/avatar/$(emailChecksum)"), "application/octet-stream")
toBase64(image)
```
Output:
```json5
This example outputs a Base64 encoded string. The resulting string was shortened for readability purposes:
```
---
### `toHex(bin:Binary): String`
Transforms a binary value into a hexadecimal string.

Example:
```json5
var myBinary = "Mule" as Binary
var testType = typeOf(myBinary)
{
   "binaryToHex" : toHex(myBinary)
}
```
Output:
```json5
{ "binaryToHex": "4D756C65" }
```
---
### `writeLinesWith(array:Array, str:String): Binary`
Writes the specified lines and returns the binary content.

Example:
```json5
{ lines: to(1, 10) map "Line $" writeLinesWith  "UTF-8" }
```
Output:
```json5
{
  "lines": "Line 1\nLine 2\nLine 3\nLine 4\nLine 5\n"
}
```
---
## Numbers
This module contains helper functions to work with Numbers.
### `fromBinary(str:String|Null): Number`
Transforms from a binary number into a decimal number.

Example:
```json5
{
    a: fromBinary("-10"),
    b: fromBinary("11111000111010111010110100101011100001001110000011010101100010111101001011100000100010011000011101100101101001111101111010110010010100110010100100000000000000000000000000000000000000000000000000000000000000"),
    c: fromBinary(0),
    d: fromBinary(null),
    e: fromBinary("100"),
}
```
Output:
```json5
{
  "a": -2,
  "b": 100000000000000000000000000000000000000000000000000000000000000,
  "c": 0,
  "d": null,
  "e": 4
}
```
---
### `fromHex(str:String|Null): Number`
Transforms a hexadecimal number into decimal number.

Example:
```json5
{
    a: fromHex("-1"),
    b: fromHex("3e3aeb4ae1383562f4b82261d969f7ac94ca4000000000000000"),
    c: fromHex(0),
    d: fromHex(null),
    e: fromHex("f"),
}
```
Output:
```json5
{
  "a": -1,
  "b": 100000000000000000000000000000000000000000000000000000000000000,
  "c": 0,
  "d": null,
  "e": 15
}
```
---
### `fromRadixNumber(str:String, num:Number):Number`
Transforms a number in the specified radix into decimal number

Example:
```json5
{
    a: fromRadixNumber("10", 2),
    b: fromRadixNumber("FF", 16)
}
```
Output:
```json5
{
  "a": 2,
  "b": 255
}
```
---
### `toBinary(num:Number|Null):String`
Transforms a decimal number into a binary number.

Example:
```json5
{
    a: toBinary(-2),
    b: toBinary(100000000000000000000000000000000000000000000000000000000000000),
    c: toBinary(0),
    d: toBinary(null),
    e: toBinary(2),
}
```
Output:
```json5
{
  "a": "-10",
  "b": "11111000111010111010110100101011100001001110000011010101100010111101001011100000100010011000011101100101101001111101111010110010010100110010100100000000000000000000000000000000000000000000000000000000000000",
  "c": "0",
  "d": null,
  "e": "10"
}
```
---
### `toHex(num:Number|Null):String`
Transforms a decimal number into a hexadecimal number.

Example:
```json5
{
    a: toHex(-1),
    b: toHex(100000000000000000000000000000000000000000000000000000000000000),
    c: toHex(0),
    d: toHex(null),
    e: toHex(15),
}
```
Output:
```json5
{
  "a": "-1",
  "b": "3e3aeb4ae1383562f4b82261d969f7ac94ca4000000000000000",
  "c": "0",
  "d": null,
  "e": "f"
}
```
---
### `toRadixNumber(num:Number, num:Number): String`
Transforms a decimal number into a number string in other radix.

Example:
```json5
{
    a: toRadixNumber(2, 2),
    b: toRadixNumber(255, 16)
}
```
Output:
```json5
{
  "a": "10",
  "b": "ff"
}
```
---
## Objects
This module contains helper functions to work with Objects.
### `divideBy(obj:Object, num:Number): Array`
Breaks up an object into sub-objects that contain the specified number of key-value pairs.

If there are fewer key-value pairs in an object than the specified number, the function will fill the object with those pairs. If there are more pairs, the function will fill another object with the extra pairs.

Example:
```json5
{ "divideBy" : DW.Objects.divideBy({"a": 1, "b" : true, "a" : 2, "b" : false, "c" : 3}, 2) }
```
Output:
```json5
{
  "divideBy": [
    {
      "a": 1,
      "b": true
    },
    {
      "a": 2,
      "b": false
    },
    {
      "c": 3
    }
  ]
}
```
---
### `entrySet(Any):Array>`
Returns an array of key-value pairs that describe the key, value, and any attributes in the input object.

This method is Deprecated. Use entriesOf from Core module, instead.

Example:
```json5
{ "entrySet" : DW.Objects.entrySet({"a":1,"b":2}) }
```
Output:
```json5
{
  "entrySet": [
    {
      "key": "a",
      "value": 1,
      "attributes": {
        
      }
    },
    {
      "key": "b",
      "value": 2,
      "attributes": {
        
      }
    }
  ]
}
```
---
### `everyEntry(obj:Object|Null, funct(value,key):Function):Boolean`
Returns true if every entry in the object matches the condition.

The function stops iterating after the first negative evaluation of an element in the object.

Example:
```json5
{
    a: DW.Objects.everyEntry({},function(value, key)std.isString(value)),
    b: DW.Objects.everyEntry({a: "", b: "123"}, function(value, key) std.isString(value)),
    c: DW.Objects.everyEntry({a: "", b: 123},function(value, key) std.isString(value)),
    d: DW.Objects.everyEntry({a: "", b: 123},function(value, key) key == "a"),
    e: DW.Objects.everyEntry({a: ""}, function(value, key) key == "b"),
    f: DW.Objects.everyEntry(null, function(value, key) key == "a")
}
```
Output:
```json5
{
  "a": true,
  "b": true,
  "c": false,
  "d": false,
  "e": true,
  "f": true
}
```
---
### `keySet(obj:Object):Array`
Returns an array of key names from an object.

This method is Deprecated. Use keysOf from Core module, instead.

Example:
```json5
{ "keySet" : DW.Objects.keySet({ "a" : true, "b" : 1}) }
```
Output:
```json5
{ "keySet" : ["a","b"] }
```
---
### `mergeWith(obj1:Object|Null, obj2:Object|Null):Object`
Appends any key-value pairs from a source object to a target object.

If source and target objects have the same key, the function appends that source object to the target and removes that target object from the output.

Example:
```json5
DW.Objects.mergeWith({ "a" : true, "b" : 1},{ "a" : false, "c" : "Test"})
```
Output:
```json5
{
    "b": 1,
    "a": false,
    "c": "Test"
}
```
---
### `nameSet(obj:Object):Array`
Returns an array of keys from an object.

This method is Deprecated. Use namesOf from Core module, instead.

Example:
```json5
{ "nameSet" : DW.Objects.nameSet({ "a" : true, "b" : 1}) }
```
Output:
```json5
{ "nameSet" : ["a","b"] }
```
---
### `someEntry(obj:Object|Null, funct(value,key):Functio):Boolean`
Returns true if at least one entry in the object matches the specified condition.

The function stops iterating after the first element that matches the condition is found.

Example:
```json5
{
    a: DW.Objects.someEntry({},function(value, key)std.isString(value)),
    b: DW.Objects.someEntry({a: "", b: "123"}, function(value, key) std.isString(value)),
    c: DW.Objects.someEntry({a: "", b: 123},function(value, key) std.isString(value)),
    d: DW.Objects.someEntry({a: "", b: 123},function(value, key) key == "a"),
    e: DW.Objects.someEntry({a: ""}, function(value, key) key == "b"),
    f: DW.Objects.someEntry(null, function(value, key) key == "a")
}
```
Output:
```json5
{
  "a": false,
  "b": true,
  "c": true,
  "d": true,
  "e": false,
  "f": false
}
```
---
### `takeWhile(obj:Object, funct(value,key):Function):Object`
Selects key-value pairs from the object while the condition is met.

Example:
```json5
local obj = {
  "a": 1,
  "b": 2,
  "c": 5,
  "d": 1
};
DW.Objects.takeWhile(obj, function(value,key) value < 3)
```
Output:
```json5
{
  "a": 1,
  "b": 2
}
```
---
### `valueSet(obj:Object):Array`
Returns an array of the values from key-value pairs in an object.

This method is Deprecated. Use valuesOf from Core module, instead.

Example:
```json5
{ "valueSet" : DW.Objects.valueSet({a: true, b: 1}) }
```
Output:
```json5
{ "valueSet" : [true,1] }
```
---
## Strings
This module contains helper functions to work with Strings.

### `appendIfMissing(str1:String|Null, str2:String):String`
Appends the suffix to the end of the text if the text does not already ends with the suffix.

Example:
```json5
{
  "a": DW.Strings.appendIfMissing(null, ""),
  "b": DW.Strings.appendIfMissing("abc", ""),
  "c": DW.Strings.appendIfMissing("", "xyz") ,
  "d": DW.Strings.appendIfMissing("abc", "xyz") ,
  "e": DW.Strings.appendIfMissing("abcxyz", "xyz")
}
```
Output:
```json5
{
  "a": null,
  "b": "abc",
  "c": "xyz",
  "d": "abcxyz",
  "e": "abcxyz"
}
```
---
### `camelize(str:String|Null):String`
Returns a string in camel case based on underscores in the string.

All underscores are deleted, including any underscores at the beginning of the string.

Example:
```json5
{
  "a" : DW.Strings.camelize("customer_first_name"),
  "b" : DW.Strings.camelize("_name_starts_with_underscore")
}
```
Output:
```json5
{
   "a": "customerFirstName",
   "b": "nameStartsWithUnderscore"
}
```
---
### `capitalize(str:String|Null):String`
Capitalizes the first letter of each word in a string.

It also removes underscores between words and puts a space before each capitalized word.

Example:
```json5
{
  "a" : DW.Strings.capitalize("customer"),
  "b" : DW.Strings.capitalize("customer_first_name"),
  "c" : DW.Strings.capitalize("customer NAME"),
  "d" : DW.Strings.capitalize("customerName")
}
```
Output:
```json5
{
  "a": "Customer",
  "b": "Customer First Name",
  "c": "Customer Name",
  "d": "Customer Name"
}
```
### `charCode(str:String):Number`
Returns the Unicode for the first character in an input string.

For an empty string, the function fails and returns Unexpected empty string.

Example:
```json5
{
  "charCode" : DW.Strings.charCode("Mule")
}
```
Output:
```json5
{ "charCode" : 77 }
```
### `charCodeAt(str:String, num:Number):Number`
Returns the Unicode for a character at the specified index.

This function fails if the index is invalid.

Example:
```json5
{
  "charCodeAt" : DW.Strings.charCodeAt("MuleSoft", 1)
}
```
Output:
```json5
{ "charCodeAt": 117 }
```
---
### `dasherize(str:String|Null):String`
Replaces spaces, underscores, and camel-casing in a string with dashes (hyphens).

If no spaces, underscores, and camel-casing are present, the output will match the input.

Example:
```json5
{
  "a" : DW.Strings.dasherize("customer"),
  "b" : DW.Strings.dasherize("customer_first_name"),
  "c" : DW.Strings.dasherize("customer NAME"),
  "d" : DW.Strings.dasherize("customerName")
}
```
Output:
```json5
{
  "a": "customer",
  "b": "customer-first-name",
  "c": "customer-name",
  "d": "customer-name"
}
```
---
### `fromCharCode(num:Number):String`
Returns a character that matches the specified Unicode.

Example:
```json5
{
  "fromCharCode" : DW.Strings.fromCharCode(117)
}
```
Output:
```json5
{ "fromCharCode": "u" }
```
---
### `isAlpha(str:String|Null):Boolean`
Checks if the text contains only Unicode digits. A decimal point is not a Unicode digit and returns false.

Note that the method does not allow for a leading sign, either positive or negative.

Example:
```json5
{
  "a": DW.Strings.isAlpha(null),
  "b": DW.Strings.isAlpha(""),
  "c": DW.Strings.isAlpha("  "),
  "d": DW.Strings.isAlpha("abc"),
  "e": DW.Strings.isAlpha("ab2c"),
  "f": DW.Strings.isAlpha("ab-c")
}
```
Output:
```json5
{
  "a": false,
  "b": false,
  "c": false,
  "d": true,
  "e": false,
  "f": false
}
```
---
### `isAlphanumeric(str:String|Null):Boolean`
Checks if the text contains only Unicode letters or digits.

Example:
```json5
{
  "a": DW.Strings.isAlphanumeric(null),
  "b": DW.Strings.isAlphanumeric(""),
  "c": DW.Strings.isAlphanumeric("  "),
  "d": DW.Strings.isAlphanumeric("abc"),
  "e": DW.Strings.isAlphanumeric("ab c"),
  "f": DW.Strings.isAlphanumeric("ab2c"),
  "g": DW.Strings.isAlphanumeric("ab-c")
}
```
Output:
```json5
{
  "a": false,
  "b": false,
  "c": false,
  "d": true,
  "e": false,
  "f": true,
  "g": false
}
```
---
### `isLowerCase(str:String|Null):Boolean`
Checks if the text contains only lowercase characters.

Example:
```json5
{
  "a": DW.Strings.isLowerCase(null),
  "b": DW.Strings.isLowerCase(""),
  "c": DW.Strings.isLowerCase("  "),
  "d": DW.Strings.isLowerCase("abc"),
  "e": DW.Strings.isLowerCase("aBC"),
  "f": DW.Strings.isLowerCase("a c"),
  "g": DW.Strings.isLowerCase("a1c"),
  "h": DW.Strings.isLowerCase("a/c")
}
```
Output:
```json5
{
  "a": false,
  "b": false,
  "c": false,
  "d": true,
  "e": false,
  "f": false,
  "g": false,
  "h": false
}
```
---
### `isNumeric(str:String|Null):Boolean`
Checks if the text contains only Unicode digits.

A decimal point is not a Unicode digit and returns false. Note that the method does not allow for a leading sign, either positive or negative.

Example:
```json5
{
  "a": DW.Strings.isNumeric(null),
  "b": DW.Strings.isNumeric(""),
  "c": DW.Strings.isNumeric("  "),
  "d": DW.Strings.isNumeric("123"),
  "e": DW.Strings.isNumeric("१२३"),
  "f": DW.Strings.isNumeric("12 3"),
  "g": DW.Strings.isNumeric("ab2c"),
  "h": DW.Strings.isNumeric("12-3"),
  "i": DW.Strings.isNumeric("12.3"),
  "j": DW.Strings.isNumeric("-123"),
  "k": DW.Strings.isNumeric("+123")
}
```
Output:
```json5
{
  "a": false,
  "b": false,
  "c": false,
  "d": true,
  "e": true,
  "f": false,
  "g": false,
  "h": false,
  "i": false,
  "j": false,
  "k": false
}
```
---
### `isUpperCase(str:String|Null): Boolean`
Checks if the text contains only uppercase characters.

Example:
```json5
{
  "a": DW.Strings.isUpperCase(null),
  "b": DW.Strings.isUpperCase(""),
  "c": DW.Strings.isUpperCase("  "),
  "d": DW.Strings.isUpperCase("ABC"),
  "e": DW.Strings.isUpperCase("aBC"),
  "f": DW.Strings.isUpperCase("A C"),
  "g": DW.Strings.isUpperCase("A1C"),
  "h": DW.Strings.isUpperCase("A/C")
}
```
Output:
```json5
{
  "a": false,
  "b": false,
  "c": false,
  "d": true,
  "e": false,
  "f": false,
  "g": false,
  "h": false
}
```
---
### `isWhitespace(str:String|Null): Boolean`
Checks if the text contains only whitespace.

Example:
```json5
{
  "a": DW.Strings.isWhitespace(null),
  "b": DW.Strings.isWhitespace(""),
  "c": DW.Strings.isWhitespace("  "),
  "d": DW.Strings.isWhitespace("abc"),
  "e": DW.Strings.isWhitespace("ab2c"),
  "f": DW.Strings.isWhitespace("ab-c")
}
```
Output:
```json5
{
  "a": false,
  "b": true,
  "c": true,
  "d": false,
  "e": false,
  "f": false
}
```
---
### `leftPad(str:String|Null, num:Number): String`
The specified text is left-padded to the size using the padText. By default padText is " ".

Returns left-padded String or original String if no padding is necessary.

Example:
```json5
{
   "a": DW.Strings.leftPad(null, 3),
   "b": DW.Strings.leftPad("", 3),
   "c": DW.Strings.leftPad("bat", 5),
   "d": DW.Strings.leftPad("bat", 3),
   "e": DW.Strings.leftPad("bat", -1)
}
```
Output:
```json5
{
  "a": null,
  "b": "   ",
  "c": "  bat",
  "d": "bat",
  "e": "bat"
}
```
---
### `ordinalize(num:Number|Null):String`
Returns a number as an ordinal, such as 1st or 2nd.

Example:
```json5
{
  "a": DW.Strings.ordinalize(1),
  "b": DW.Strings.ordinalize(2),
  "c": DW.Strings.ordinalize(5),
  "d": DW.Strings.ordinalize(103)
}
```
Output:
```json5
{
   "a": "1st",
   "b": "2nd",
   "c": "5th",
   "d": "103rd"
}
```
---
### `pluralize(str:String|Null): String`
Pluralizes a singular string.

If the input is already plural (for example, "boxes"), the output will match the input.

Example:
```json5
{ "pluralize" : DW.Strings.pluralize("box") }
```
Output:
```json5
{ "pluralize" : "boxes" }
```
---
### `prependIfMissing(str1:String|Null, str2:String):String`
Prepends the prefix to the beginning of the string if the text does not already start with that prefix.

Example:
```json5
{
  "a": DW.Strings.prependIfMissing(null, ""),
  "b": DW.Strings.prependIfMissing("abc", ""),
  "c": DW.Strings.prependIfMissing("", "xyz"),
  "d": DW.Strings.prependIfMissing("abc", "xyz"),
  "e": DW.Strings.prependIfMissing("xyzabc", "xyz")
}
```
Output:
```json5
{
  "a": null,
  "b": "abc",
  "c": "xyz",
  "d": "xyzabc",
  "e": "xyzabc"
}
```
---
### `repeat(str:String, num:Number):String`
Repeats a text the number of specified times.

Example:
```json5
{
  "a": DW.Strings.repeat("e", 0),
  "b": DW.Strings.repeat("e", 3),
  "c": DW.Strings.repeat("e", -2)
}
```
Output:
```json5
{
  "a": "",
  "b": "eee",
  "c": ""
}
```
---
### `rightPad(str:String|Null, num:Number):String`
The specified text is right-padded to the size using the padText. By default padText is " ".

Returns right padded String or original String if no padding is necessary.

Example:
```json5
{
  "a": DW.Strings.rightPad(null, 3),
  "b": DW.Strings.rightPad("", 3),
  "c": DW.Strings.rightPad("bat", 5),
  "d": DW.Strings.rightPad("bat", 3),
  "e": DW.Strings.rightPad("bat", -1)
}
```
Output:
```json5
{
  "a": null,
  "b": "   ",
  "c": "bat  ",
  "d": "bat",
  "e": "bat"
}
```
---
### `singularize(str:String|Null):String`
Converts a plural string to its singular form.

If the input is already singular (for example, "box"), the output will match the input.

Example:
```json5
{ "singularize" : DW.Strings.singularize("boxes") }
```
Output:
```json5
{ "singularize" : "box" }
```
---
### `substringAfter(str1:String|Null, str2:String):String`
Gets the substring after the first occurrence of a separator. The separator is not returned.

Example:
```json5
{
  "a": DW.Strings.substringAfter(null, "'"),
  "b": DW.Strings.substringAfter("", "-"),
  "c": DW.Strings.substringAfter("abc", "b"),
  "d": DW.Strings.substringAfter("abcba", "b"),
  "e": DW.Strings.substringAfter("abc", "d"),
  "f": DW.Strings.substringAfter("abc", "")
}
```
Output:
```json5
{

  "a": null,
  "b": "",
  "c": "c",
  "d": "cba",
  "e": "",
  "f": "bc"
}
```
---
### `substringAfterLast(str:String|Null, sep:String):String`
Gets the substring after the last occurrence of a separator. The separator is not returned.

Example:
```json5
{
  "a": DW.Strings.substringAfterLast(null, "'"),
  "b": DW.Strings.substringAfterLast("", "-"),
  "c": DW.Strings.substringAfterLast("abc", "b"),
  "d": DW.Strings.substringAfterLast("abcba", "b"),
  "e": DW.Strings.substringAfterLast("abc", "d"),
  "f": DW.Strings.substringAfterLast("abc", "")
}
```
Output:
```json5
{
 "a": null,
 "b": "",
 "c": "c",
 "d": "a",
 "e": "",
 "f": null
}
```
---
### `substringBefore(str:String|Null, sep:String):String`
Gets the substring before the first occurrence of a separator. The separator is not returned.

Example:
```json5
{
  "a": DW.Strings.substringBefore(null, "'"),
  "b": DW.Strings.substringBefore("", "-"),
  "c": DW.Strings.substringBefore("abc", "b"),
  "d": DW.Strings.substringBefore("abc", "c"),
  "e": DW.Strings.substringBefore("abc", "d"),
  "f": DW.Strings.substringBefore("abc", "")
}
```
Output:
```json5
{
  "a": null,
  "b": "",
  "c": "a",
  "d": "ab",
  "e": "",
  "f": ""
}
```
---
### `substringBeforeLast(str:String|Null, sep:String):String`
Gets the substring before the last occurrence of a separator. The separator is not returned.

Example:
```json5
{
  "a": DW.Strings.substringBeforeLast(null, "'"),
  "b": DW.Strings.substringBeforeLast("", "-"),
  "c": DW.Strings.substringBeforeLast("abc", "b"),
  "d": DW.Strings.substringBeforeLast("abcba", "b"),
  "e": DW.Strings.substringBeforeLast("abc", "d"),
  "f": DW.Strings.substringBeforeLast("abc", "")
}
```
Output:
```json5
{
  "a": null,
  "b": "",
  "c": "a",
  "d": "abc",
  "e": "",
  "f": "ab"
}
```
---
### `underscore(str:String|Null): String`
Replaces hyphens, spaces, and camel-casing in a string with underscores.

If no hyphens, spaces, and camel-casing are present, the output will match the input.

Example:
```json5
{
   "a" : DW.Strings.underscore("customer"),
   "b" : DW.Strings.underscore("customer-first-name"),
   "c" : DW.Strings.underscore("customer NAME"),
   "d" : DW.Strings.underscore("customerName")
}
```
Output:
```json5
{
   "a": "customer",
   "b": "customer_first_name",
   "c": "customer_name",
   "d": "customer_name"
}
```
---
### `unwrap(str:String|Null, wrapper:String):String`
Unwraps a given text from a wrapper text.

Example:
```json5
{
  "a": DW.Strings.unwrap(null, ""),
  "b": DW.Strings.unwrap(null, '\0'),
  "c": DW.Strings.unwrap("'abc'", "'"),
  "d": DW.Strings.unwrap("AABabcBAA", 'A'),
  "e": DW.Strings.unwrap("A", '#'),
  "f": DW.Strings.unwrap("#A", '#'),
  "g": DW.Strings.unwrap("A#", '#')
}
```
Output:
```json5
{
  "a": null,
  "b": null,
  "c": "abc",
  "d": "ABabcBA",
  "e": "A",
  "f": "A#",
  "g": "#A"
}
```
---
### `withMaxSize(str:String|Null, num:Number):String`
Checks that the string length is no larger than the specified maxLength. If the string’s length is larger than the maxLength, the function cuts characters from left to right, until the string length meets the length limit.

Example:
```json5
{
   a: DW.Strings.withMaxSize("123",10),
   b: DW.Strings.withMaxSize("123",3),
   c: DW.Strings.withMaxSize("123",2),
   d: DW.Strings.withMaxSize("",0),
   e: DW.Strings.withMaxSize(null,23)
}
```
Output:
```json5
{
  "a": "123",
  "b": "123",
  "c": "12",
  "d": "",
  "e": null
}
```
---
### `wrapIfMissing(str:String|Null, wrapper:String):String`
Wraps text with wrapper if that wrapper is missing from the start or end of the given string.

Example:
```json5
{
   "a": DW.Strings.wrapIfMissing(null, "'"),
   "b": DW.Strings.wrapIfMissing("", "'"),
   "c": DW.Strings.wrapIfMissing("ab", "x"),
   "d": DW.Strings.wrapIfMissing("'ab'", "'"),
   "e": DW.Strings.wrapIfMissing("/", '/'),
   "f": DW.Strings.wrapIfMissing("a/b/c", '/'),
   "g": DW.Strings.wrapIfMissing("/a/b/c", '/'),
   "h": DW.Strings.wrapIfMissing("a/b/c/", '/')
}
```
Output:
```json5
{
   "a": null,
   "b": "'",
   "c": "xabx",
   "d": "'ab'",
   "e": "/",
   "f": "/a/b/c/",
   "g": "/a/b/c/",
   "h": "/a/b/c/"
}
```
---
### `wrapWith(str:String|Null, wrapper:String):String`
Wraps the specified text with the given wrapper.

Example:
```json5
{
  "a": DW.Strings.wrapWith(null, "'"),
  "b": DW.Strings.wrapWith("", "'"),
  "c": DW.Strings.wrapWith("ab", "x"),
  "d": DW.Strings.wrapWith("'ab'", "'"),
  "e": DW.Strings.wrapWith("ab", "'")
}
```
Output:
```json5
{
  "a": null,
  "b": "''",
  "c": "xabx",
  "d": "''ab''",
  "e": "'ab'"
}
```
---
