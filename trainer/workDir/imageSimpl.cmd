CreateTraining: width:350,height:350,type:RGB
Input: image:./dalmatin/1.jpg, identity:dalmatin
#Input: image:./dalmatin/2.jpg, identity:dalmatin
#Input: image:./dalmatin/3.jpg, identity:dalmatin
Network: hidden1:120, hidden2:100, hidden3:70, hidden4:0, hidden5:0
SaveTrainData: file: ./dogs.egb
#SaveNet: file:./dogs.eg
Train: Mode:guic, Minutes:1, StrategyError:0.5, StrategyCycles:150
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