<body>
    <div>
        <h2>作品名稱: 線上學習go</h2>
    </div>
    <ol>
        <li>
            <a href="http://13.56.232.122:8080/">線上學習go</a>
            <span style="margin-right: 20px; font-size: 15px; font-weight: normal;">於2022/09完成</span>
            <>
            <div>
                線上學習go的講師跟課程資料有用後台管理，技術是使用Spring Boot + React.js + MySQL，相關原始碼及畫面在<a href="https://github.com/brianchen712/ecommerce-data">這裡</a>
            </div>
        </li>
        <br>
        <li>
            作品說明: 使用Spring Boot框架 + Thymeleaf模板引擎 + MySQL做的線上課程網站，已將網站部屬到AWS EC2 Server上
        </li>
        <br>
        <li>
            使用技術說明:
            <ul>
                <li>使用Spring Security進行使用者登入、登出的處理</li>
                <li>使用Spring AOP紀錄LOG資訊</li>
                <li>使用Spring Data JPA 存取DB資料</li>
                <li>使用模板引擎Thymeleaf，並使用網路上的Bootstrap Template撰寫網站畫面</li>
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
                            <br>
                            *點選註冊按鈕，檢核所有欄位是否填寫，密碼、確認密碼是否一致，以及信箱格式是否正確
                        </li>
                        <br>
                        <li>
                            欄位資料沒問題，按下註冊後，可註冊成功
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/registration2.png">
                            <br>
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
                                    *可點選加入購物車按鈕或立即購買，點選後顯示內容請參照1、2
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
                            *點選選單旁邊的線上學習go，可回到首頁，每個頁面的線上學習go點擊後皆會跳轉至首頁
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
                             <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/frontend2.png">
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
                    <h4>我的資料</h4>
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
                <li>
                    <h4>我的訂單</h4>
                    <ul>
                        <li>
                            點選個人資訊->我的訂單
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myOrder1.png">
                        </li>
                        <li>
                            我的訂單頁會顯示結帳後產生的訂單
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myOrder2.png">
                        </li>
                        <li>
                            點選查看明細，可以查看購買課程清單
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myOrder3.png">
                        </li>
                    </ul>
                </li>
                <li>
                    <h4>我的信用卡</h4>
                    <ul>
                        <h4>查看信用卡</h4>
                        <li>
                            點選個人資料->我的信用卡
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myCard1.png">
                            <br>
                            會顯示目前擁有的信用卡
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myCard2.png">
                            <br>
                            *第一次購物使用的信用卡會自動設成預設卡片，以後購物會自動帶入該卡片資料
                        </li>
                        <h4>新增信用卡</h4>
                        <li>
                            點選新增信用卡
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/addCard1.png">
                            <br>
                            進入新增信用卡畫面，新增卡片資訊後，按下儲存
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/addCard2.png">
                            會回到我的信用卡頁面，並顯示剛才新增的卡片資訊
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/addCard3.png">
                        </li>
                        <h4>更換預設卡片</h4>
                        <li>
                            點選設成預設
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/defaultCard1.png">
                            <br>
                            卡片是否為預設顯示為是，代表此卡片已成為預設卡片
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/defaultCard2.png">
                        </li>
                        <h4>修改信用卡</h4>
                        <li>
                            點選修改資料
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/modifyCard1.png">
                            <br>
                            進入修改信用卡畫面，修改卡片資料後，按下儲存
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/modifyCard2.png">
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/modifyCard3.png">
                            <br>
                            回到我的信用卡畫面，再點選該資料，會顯示修改後的資料內容
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/modifyCard4.png">
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/modifyCard5.png">
                        </li>
                        <h4>移除信用卡</h4>
                        <li>
                            對卡片按下x，即可移除卡片
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/removeCard1.png">
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/removeCard2.png">
                        </li>
                    </ul>
                </li>
                <li>
                    <h4>我的課程</h4>
                    <ul>
                        <h4>查看課程</h4>
                        <li>
                            點選個人資料->我的課程
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myCourse1.png">
                            <br>
                            進入我的課程頁，會顯示購買過的課程
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/myCourse2.png">
                        </li>
                        <h4>對課程評分</h4>
                        <li>
                            點選開始上課，可進入課程內容頁，跟首頁、課程明細頁下方的開始上課按鈕一樣效果
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/courseContent1.png">
                            <br>
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/courseContent2.png">
                        </li>
                        <li>
                            移至畫面下方，可對該課程填寫評論
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/courseContent3.png">
                        </li>
                        <li>
                            填寫完畢後，按下送出
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/courseContent4.png">
                        </li>
                        <li>
                            畫面下方會顯示剛儲存的評論
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/courseContent5.png">
                        </li>
                        <li>
                            回到首頁，課程也會多一個評分
                            <img src="https://github.com/brianchen712/ecommerce-project/blob/master/Screenshots/homeHasRating.png">
                        </li>
                    </ul>
                </li>
            </ol>
        </li>
    </ol>
</body>
