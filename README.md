# DiscordTweetBot
本アプリは、Twitter上にDiscordボイスチャンネルへの入室/退室やゲーム起動等を通知するbotアカウントを動作させるアプリケーションです。  

## 用意するもの
- 本アプリケーション  
→ Download
- Java Version 8以上  
→ Download
- 通知を行うTwitterアカウント
- Discordアカウント
- 本アプリケーションを動作させるPC

## 使い方
1. 本アプリケーションをダウンロードし、ファイルを解凍します
1. DiscordTweetBot.exeを起動します
1. 初回起動時はメッセージとともに、このページが表示されます
1. メイン画面が表示されていることを確認してください  
![メイン画面](https://gist.githubusercontent.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/main1.png)
1. [Login to Twitter]ボタンを押して、通知ツイートを行いたいアカウントでログインをしてください
1. PIN番号を、ダイアログボックスに入力してください
1. [Login to Discord]ボタンを押すと、Discordボットアカウントの設定を行います
1. Discordのログイン画面が表示されるので**普段使用しているアカウント**でログインをしてください  
![ログイン画面](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/discord1.png)
1. My Appsページが表示されるので、**New App**を選択して新しいBOTアカウントを作成してください  
![My Appsページ](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/discord2.png)
1. 必須項目は「**APP NAME**」欄だけですが、必要に応じて他の項目も入力してください  
![New Appページ](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/discord3.png)
1. 作成が終了すると情報ページへ遷移するので、赤枠の「**ClientID**」と「**Token**」を用意してください。Tokenは「click to reveal」を押すと表示されます  
![情報ページ](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/discord4.png)
1. 先程のClientIDとTokenを、入力ダイアログにそれぞれ入力してください  
![ClientID](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/main2.png)  
![Token](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/b58c6f2f98836edd6428b15ce8d42b1084b14732/main3.png)
1. メイン画面に戻り、2つのボタンが「**Logged in as アカウント名**」と表示されていることを確認します
1. 3つのチェックボックスで、Twitterへ通知を行う項目を選択できます。  
    - Notify Voice Channel Joining / Leaving  
    誰かがボイスチャンネルに入室/退室したとき通知を行います
    - Notify Member Starting Game  
    誰かのステータスが「ゲームをプレー中」になったとき通知を行います
    - Notify Invitation for Game  
    テキストチャットで「invite」が送信されたとき、通知を行います
1. 「**Launch**」ボタンを押すことでBOTが動作を開始します  
Discord上でBOTがオンラインになっていることを確認してください  
![オンライン状態](https://gist.github.com/advancedbear/1feda466fc8b00d2ae0b863ac7226bd3/raw/fdceeff90750e669497988f896b775d12ce001bc/discord5.png)
1. 2回目以降の起動では上記設定は不要で、「**Launch**」ボタンを押すだけで動作が開始されます

## 動かないときは
症状と環境を併記して、[作者Twitter](https://twitter.com/advanced_bear)までご一報をください。

## ライセンス
本アプリケーションはLGPL3.0ライセンスが適用されています。
本アプリケーションでは[Discord4Jライブラリ](https://github.com/austinv11/Discord4J/)を使用しています。