import {useEffect} from "react";

function Login() {

    const { naver, Kakao } = window

    const onKakaoLogin = () => {
        Kakao.Auth.authorize({
            redirectUri: 'http://localhost:3000/signin/kakao',
            serviceTerms: 'marketing_agree'
        });
    }

    const initializeNaverLogin = () => {
        const naverLogin = new naver.LoginWithNaverId({
            clientId: 'vvB00rCE3LmrAIz8ZyZF',
            callbackUrl: 'http://127.0.0.1:3000/signin/naver',
            // 팝업창으로 로그인을 진행할 것인지?
            isPopup: false,
            // 버튼 타입 ( 색상, 타입, 크기 변경 가능 )
            loginButton: { color: 'green', type: 3, height: 58 },
            callbackHandle: true
        })
        naverLogin.init()
    }

    useEffect(() => {
        initializeNaverLogin()
    }, [])

    return (
        <div className="App">
            <button onClick={onKakaoLogin}>카카오 로그인</button>
            <div id="naverIdLogin" />
        </div>
    );
}

export default Login;

