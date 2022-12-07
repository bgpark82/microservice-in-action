import {useLocation} from "react-router-dom";
import {useEffect} from "react";
import axios from "axios";
import qs from "qs";


const Kakao = () => {

    const location = useLocation()

    useEffect(() => {
        const CLIENT_ID = '9ca06bbd0fe1dc87b284be0803409438'; // 마이크로서비스 테스트
        // const CLIENT_ID = 'eb579813b6c7b1c40b15ce236f572fe1'; // 베이비페이스 테스트
        // const ACCESS_TOKEN_URL = `https://dev-bf-rest.babyface.io/social/login/kakao`;
        const ACCESS_TOKEN_URL = `http://localhost:8080/social/login/kakao`;
        // const ACCESS_TOKEN_URL = `http://localhost:9090/social/login/kakao`;

        const code = qs.parse(location.search, {ignoreQueryPrefix: true})
        axios.post(`https://kauth.kakao.com/oauth/token`,{
            grant_type:'authorization_code',
            client_id: 'eb579813b6c7b1c40b15ce236f572fe1',
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
                })
        })
    },[])

    return (<div>kakao</div>)
}

export default Kakao;
