import librosa
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def showMFCCby20(df):
    for i in range(20) :
        Y = np.array(df.loc[i, :])
        X = np.arange(Y.size)
        print("\nI : ", i,"\nX : ", X, "\nY : ", Y)
        plt.scatter(X, Y)
        plt.plot(X, Y)

def showMFCCbyMean(df):
    print(df.mean())
    Y = np.array(df.mean())
    X = np.arange(Y.size)
    plt.scatter(X,Y)
    plt.plot(Y)
    return Y

voiceLocation = 'voice sample'

y0,sr0=librosa.core.load(voiceLocation+"/KimBeomSoo/김범수_끝사랑(sr8000)_1절verse.wav",sr=8000,duration=10)
y1,sr1=librosa.core.load(voiceLocation+"/KimBeomSoo/김범수_끝사랑(sr8000)_2절verse.wav",sr=8000,duration=10)
y2,sr2=librosa.core.load(voiceLocation+"/KimBeomSoo/김범수_끝사랑(sr8000)_1절후렴.wav",sr=8000,duration=10)
y3,sr3=librosa.core.load(voiceLocation+"/KimBeomSoo/김범수_끝사랑(sr8000)_2절후렴.wav",sr=8000,duration=10)
y4,sr4=librosa.core.load(voiceLocation+"/MCtheMax/mcthemax_My way(1절).wav",sr=8000,duration=10)
y5,sr5=librosa.core.load(voiceLocation+"/MCtheMax/mcthemax_My way(2절).wav",sr=8000,duration=10)

mfcc0 = librosa.feature.mfcc(y=y0,sr=sr0)
mfcc1 = librosa.feature.mfcc(y=y1,sr=sr1)
mfcc2 = librosa.feature.mfcc(y=y2,sr=sr2)
mfcc3 = librosa.feature.mfcc(y=y3,sr=sr3)
mfcc4 = librosa.feature.mfcc(y=y4,sr=sr4)
mfcc5 = librosa.feature.mfcc(y=y5,sr=sr5)

d0=pd.DataFrame(mfcc0)
d1=pd.DataFrame(mfcc1)
d2=pd.DataFrame(mfcc2)
d3=pd.DataFrame(mfcc3)
d4=pd.DataFrame(mfcc4)
d5=pd.DataFrame(mfcc5)

d00 = d0.loc[2, :]
d10 = d1.loc[2, :]
d20 = d2.loc[2, :]
d30 = d3.loc[2, :]
d40 = d4.loc[2, :]
d50 = d5.loc[2, :]

target1 = d00
target2 = d20
target3 = d40

target1.plot()
target2.plot()
target3.plot()

Y1 = np.array(d20.mean())
Y2 = np.array(d40.mean())
print(abs(Y1-Y2))
plt.show()
