CreateTraining: width:128,height:128,type:RGB
Input: image:./book.png, identity:book
Input: image:./book2.png, identity:book
Input: image:./book3.png, identity:book
Input: image:./girl1.png, identity:girl
Input: image:./girl2.png, identity:girl
Input: image:./girl3.png, identity:girl
Network: hidden1:20, hidden2:3
Train: Mode:console, Minutes:1, StrategyError:0.25, StrategyCycles:50
Whatis: image:./book_r.png
Whatis: image:./girl_r.png
