<body>
    <div>
        <h2>作品名稱: 線上學習go</h2>
    </div>
    <ol>
        <li>
            <a href="http://13.56.232.122:8080/">線上學習go</a>
            <span style="margin-right: 20px; font-size: 15px; font-weight: normal;">於2022/09完成</span> 
        </li>
        <br>
        <li>
            作品說明: 使用Spring Boot框架 + Thymeleaf模板引擎 + MySql做的線上課程網站，已將網站部屬到AWS EC2 Server上
        </li>
        <br>
        <li>
            使用技術說明:
            <ul>
                <li>使用Spring Security進行使用者登入、登出的處理</li>
                <li>使用Spring AOP紀錄LOG資訊</li>
                <li>使用Spring Data JPA 存取DB資料</li>
                <li>使用模板引擎Thymeleaf並使用網路上的bootstrap template撰寫網站畫面</li>
            </ul>
        </li>
        <br>
        <li>
            作品功能介紹: 
            <ol type="a">
                <li>
                    <h4>註冊/登入/登出功能</h4>
                    <ul>
                        <h4>註冊功能</h4>
                        <li>
                            填寫欄位
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/registration1.png">
                            *點選註冊按鈕，檢核所有欄位是否填寫，密碼、確認密碼是否一致，以及信箱格式是否正確
                        </li>
                        <br>
                        <li>
                            欄位資料沒問題，按下註冊後，可註冊成功
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/registration2.png">
                            *若後來不想註冊，可點選回登入頁
                        </li>
                        <br>
                        <li>
                            完成註冊，會跳轉至註冊成功畫面
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/registration3.png">
                        </li>
                        <h4>登入功能</h4>
                        <li>
                            可使用註冊好的帳號、密碼進行登入，可成功進入首頁
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/signIn1.png">
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/signIn2.png">
                        </li>
                        <br>
                        <h4>登出功能</h4>
                        <li>
                            點選首頁->個人資訊->登出，即可成功登出
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/signOut1.png">
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/signOut2.png">
                        </li>
                    </ul>
                </li>
                <li>
                    <h4>購物功能</h4>
                    <ul>
                        <h4>加入購物車</h4>
                        <li>
                            選擇想要的課程加入購物車，方式有三種
                            <div>1.點選首頁課程的加入購物車按鈕</div>
                            <div>2.點選首頁課程的立即購買按鈕</div>
                            <div>3.點選首頁課程圖示，跳轉至明細頁，可點選畫面上加入購物車或立即購買按鈕</div>
                            <br>
                            以下依序說明1到3
                            <ol type="1">
                                <li>
                                    點選想要購買的課程(如下圖紅框處)，並點選加入購物車
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/addCart1.png">
                                    <br>
                                    <br>
                                    購買課程後，課程會被加入購物車，且課程下方只顯示前往購物車按鈕
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/addCart2.png">
                                </li>
                                <br>
                                <li>
                                    點選立即購買，可把課程加入購物車，並跳轉至購物車畫面
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/buyNow1.png">
                                    <br>
                                    <br>
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/buyNow2.png">
                                    <br>
                                    <div>*(1)購物車畫面有X，點選可移除購物車商品</div>
                                    <div>*(2)可點選繼續購物，回首頁繼續購物</div>
                                </li>
                                <br>
                                <li>
                                    點選首頁課程圖示，跳轉至明細頁，可點選畫面上加入購物車或立即購買按鈕
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/detailShopping1.png">
                                    <br>
                                    <br>
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/detailShopping2.png">
                                    *可點選加入購物車按鈕或立即購買，點選後續內容請參照(1)(2)
                                    <br>
                                    <br>
                                    移至畫面下方，會顯示該講師開的其他課程，若尚未加入購物車會顯示加入購物車及立即購買按鈕，反之則顯示前往購物車按鈕
                                    <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/detailShopping3.png">
                                </li>
                            </ol>
                        </li>
                        <br>
                        <h4>結帳(未串接金流)</h4>
                        <li>
                            點選購物車畫面的去結帳按鈕
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/checkout1.png">
                        </li>
                        <br>
                        <li>
                            進入結帳頁面，填寫信用卡資料
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/checkout2.png">
                            <div>*點選結帳按鈕，會檢核所有欄位是否已填，卡號、到期日格式是否正確
</div>
                            <div>*點選購物車可回到購物車畫面
</div>
                            <div>*點選安全碼旁邊的圖示，會跳出安全碼說明Popup
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/cvv2.png">
</div>
                        </li>
                        <br>
                        <li>
                            欄位資料沒問題，按下結帳會跳轉至結帳成功頁
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/checkout3.png">
                            <br>
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/checkout4.png">
                            <br>
                            *點選選單旁邊的線上學習go，可回到首頁，每個頁面的線上學習go點擊後皆有此動作
                        </li>
                        <li>
                            <div>點選回首頁</div>
                            <div>(1)首頁購物車內容會被清空，且已購買的課程下方會顯示開始上課按鈕，課程明細頁，也會顯示開始上課按鈕</div>
                            <div>(2)有人購買的課程，也會顯示目前購買人數</div>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/checkoutAndBackHome.png">
                        </li>
                    </ul>
                </li>
                <li>
                     <h4>課程類別</h4>
                     <ul>
                         <h4>所有課程</h4>
                         <li>
                             點選課程類別->所有課程
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/allCourses1.png">
                             <br>
                             畫面顯示所有課程
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/allCourses2.png">
                         </li>
                         <br>
                         <h4>前端</h4>
                         <li>
                             點選課程類別->前端
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/frontend1.png">
                             <br>
                             畫面顯示前端課程
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/frontend1.png">
                         </li>
                         <br>
                         <h4>資料庫</h4>
                         <li>
                             點選課程類別->資料庫
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/database1.png">
                             <br>
                             畫面顯示資料庫課程
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/database2.png">
                         </li>
                         <br>
                         <h4>後端</h4>
                         <li>
                             點選課程類別->後端
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/backend1.png">
                             <br>
                             畫面顯示後端課程
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/backend2.png">
                         </li>
                     </ul>
                </li>
                <li>
                    我的資料
                    <ul>
                        <li>
                            點選個人資訊->我的資料
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myProfile1.png">
                        </li>
                        <br>
                        <li>
                            進入我的資料頁，可修改資料
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myProfile2.png">
                            *欄位檢核規則同註冊功能畫面欄位
                        </li>
                        <br>
                        <li>
                            修改完畢後，資料沒問題，點選送出後，會顯示修改成功訊息
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myProfile3.png">
                            <br>
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myProfile4.png">
                        </li>
                    </ul>
                </li>            
                <li>我的訂單</li>
                <li>我的信用卡</li>
                <li>我的課程</li>
            </ol>
        </li>
    </ol>
</body>
