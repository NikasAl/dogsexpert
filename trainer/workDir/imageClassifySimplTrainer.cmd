CreateTraining: width:25,height:25,type:RGB
Input: identity:alyaskinskij-malamut
Input: identity:dalmatin
Input: identity:dzhek-rassel-terer
Input: identity:doberman
Input: identity:maltijskaya-bolonka
Input: identity:mops
Input: identity:ovcharka-nemeckaya
Input: identity:taksa
Input: identity:nyufaundlend
Input: identity:mittelshnaucer
Input: identity:english-springer-spaniel
LoadNet: file: ./theBestNet1_5prctErr_25x25_120-100-100_7h01m.eg
LoadTrainData: file: ./dogs_25x25_4325img.egb
Train: Mode:gui, Minutes:1, StrategyError:0.5, StrategyCycles:150
Whatis: image:./alyaskinskij-malamut/7.jpg
Whatis: image:./alyaskinskij-malamut/6.jpg
Whatis: image:./alyaskinskij-malamut/5.jpg
Whatis: image:./alyaskinskij-malamut/4.jpg
Whatis: image:./alyaskinskij-malamut/3.jpg
Whatis: image:./alyaskinskij-malamut/2.jpg
Whatis: image:./alyaskinskij-malamut/1.jpg
Whatis: image:./alyaskinskij-malamut/0.jpg
Whatis: image:./akita-inu/7.jpg
Whatis: image:./akita-inu/6.jpg
Whatis: image:./akita-inu/5.jpg
Whatis: image:./akita-inu/4.jpg
Whatis: image:./akita-inu/3.jpg
Whatis: image:./akita-inu/2.jpg
Whatis: image:./akita-inu/1.jpg
Whatis: image:./akita-inu/0.jpg
Whatis: image:./nyufaundlend/1201.jpg
Whatis: image:./nyufaundlend/501.jpg
Whatis: image:./nyufaundlend/301.jpg
Whatis: image:./nyufaundlend/201.jpg
Whatis: image:./nyufaundlend/1.jpg
SaveNet: file:./dogsAfterTrain.eg