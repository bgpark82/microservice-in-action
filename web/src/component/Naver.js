import {useEffect} from "react";
import axios from "axios";

function Naver() {

    const { naver } = window
    const NAVER_CLIENT_ID = 'vvB00rCE3LmrAIz8ZyZF'
    const NAVER_CALLBACK_URL = 'http://127.0.0.1:3000/signin/naver'


    const initializeNaverLogin = () => {
        const naverLogin = new naver.LoginWithNaverId({
            clientId: NAVER_CLIENT_ID,
            callbackUrl: NAVER_CALLBACK_URL,
            // 팝업창으로 로그인을 진행할 것인지?
            isPopup: false,
            // 버튼 타입 ( 색상, 타입, 크기 변경 가능 )
            loginButton: { color: 'green', type: 3, height: 58 },
            callbackHandle: true
        })
        naverLogin.getLoginStatus(function (status) {
            console.log(status)
            if (status) {
                console.log(status)
                /* 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
                const email = naverLogin.user.getEmail();
                const name = naverLogin.user.getName();
                const accessToken = naverLogin.accessToken.accessToken;
                console.log(email, name, status, accessToken)
                
                axios.post(`http://localhost:8080/social/login/naver?accessToken=${accessToken}`)
                    .then(res => {
                        console.log(res)
                    })

            } else {
                console.log('callback 처리에 실패하였습니다.');
            }
        });
    }

    useEffect(() => {
        initializeNaverLogin()
    }, [])


    return (<div>hello</div>)
}

export default Naver
