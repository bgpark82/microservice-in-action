import {useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import qs from "qs";


const Kakao = () => {

    // const CLIENT_ID = '9ca06bbd0fe1dc87b284be0803409438'; // 마이크로서비스 테스트
    const CLIENT_ID = 'eb579813b6c7b1c40b15ce236f572fe1'; // 베이비페이스 테스트
    // const ACCESS_TOKEN_URL = `https://dev-bf-rest.babyface.io/social/login/kakao`;
    // const ACCESS_TOKEN_URL = `http://localhost:8080/social/login/kakao`;
    const BASE_URL = `http://localhost:9090`;
    const ACCESS_TOKEN_URL = `${BASE_URL}/social/login/kakao`;

    const location = useLocation()

    const [token, setToken] = useState('');

    useEffect(() => {


        const code = qs.parse(location.search, {ignoreQueryPrefix: true})
        axios.post(`https://kauth.kakao.com/oauth/token`,{
            grant_type:'authorization_code',
            client_id: CLIENT_ID,
            redirect_uri: 'http://localhost:3000/signin/kakao',
            code: code.code
        }, {
            headers: {
                'content-type': 'application/x-www-form-urlencoded'
            }
        })
        .then(res => {
            const {access_token, expires_in, refresh_token, scope, token_type} = res.data
            console.log(res.data)
            axios.post(`${ACCESS_TOKEN_URL}?accessToken=${access_token}&businessName=babyface`)
                .then(res => {
                    console.log(res)
                    setToken(res.data.accessToken);
                })
        })
    },[])

    const onWithdrawal = () => {
        console.log(token)
        const config = {
            data : {
                business: "BABYFACE",
                detailReason: "",
                reason: "방문 빈도가 낮음"
            },
            headers : {
                'Authorization': token
            }
        };
        axios.delete(`${BASE_URL}/account/withdrawal`,config).then((res) => {
            console.log("회원탈퇴", res);
        })

    }

    return (<div>
        <h1>kakao</h1>
        <button onClick={onWithdrawal}>회원탈퇴</button>
    </div>)
}

export default Kakao;
