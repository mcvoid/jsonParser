grammar Json;

// These are our non-terminal rules
// Each of these will be a node on the parse tree
// and a method in the visitor.
json: value;
obj: '{' pair (',' pair)* '}' | '{' '}' ;
pair: STRING ':' value;
array: '[' value (',' value)* ']' | '[' ']';
value: STRING | NUMBER | obj | array | 'true' | 'false' | 'null';

// These are our terminal rules. The lexer uses these.
STRING: '"' (ESC | ~ ["\\])* '"';
fragment ESC: '\\' (["\\/bfnrt] | UNICODE);
fragment UNICODE: 'u' HEX HEX HEX HEX;
fragment HEX: [0-9a-fA-F];

NUMBER:
    '-'? INT '.' [0-9] + EXP?
  | '-'? INT EXP
  | '-'? INT;
fragment INT: '0' | [1-9] [0-9]*;
fragment EXP: [Ee] [+\-]? INT;

WS: [ \t\n\r]+ -> skip;
