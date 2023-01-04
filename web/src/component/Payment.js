import axios from "axios";

function Payment() {

    const PAYMENT_SERVICE_URL = "http://localhost:8000/payment-service/create-checkout-session";

    const onKGPayment = () => {
        const IMP = window.IMP
        IMP.init(process.env.REACT_APP_IAMPORT_MERCHANT_ID)

        IMP.request_pay({
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "ORD20180131-0000011",   //주문번호
            name: "노르웨이 회전 의자",
            amount: 100,                         // 숫자타입
            buyer_email: "gildong@gmail.com",
            buyer_name: "홍길동",
            buyer_tel: "010-4242-4242",
            buyer_addr: "서울특별시 강남구 신사동",
            buyer_postcode: "01181"
        }, function (rsp) { // callback
            console.log(rsp)
            if (rsp.success) {
                console.log("결제 성공")
            } else {
                console.log("결제 실패")
            }
        });
    }

    const onKakaoPayment = () => {
        const IMP = window.IMP
        IMP.init(process.env.REACT_APP_IAMPORT_MERCHANT_ID)

        IMP.request_pay({
            pg: "kakaopay",
            pay_method: "card",
            merchant_uid: "ORD20180131-0000012",   //주문번호
            name: "노르웨이 회전 의자",
            amount: 100,                         // 숫자타입
            buyer_email: "gildong@gmail.com",
            buyer_name: "홍길동",
            buyer_tel: "010-4242-4242",
            buyer_addr: "서울특별시 강남구 신사동",
            buyer_postcode: "01181"
        }, function (rsp) { // callback
            console.log(rsp)
            if (rsp.success) {
                console.log("결제 성공")
            } else {
                console.log("결제 실패")
            }
        });
    }

    const onStripePayment = async () => {
        const formData = new FormData();
        const res = await axios.post(PAYMENT_SERVICE_URL, formData)
        if (res.status == 200) {
            console.log(res.data)
            window.location = res.data
        }
    }

    return (
        <div>
            <button onClick={onKGPayment}>KG이니시스</button>
            <button onClick={onKakaoPayment}>카카오페이</button>
            <button onClick={onStripePayment}>Stripe</button>
        </div>
    )
}

export default Payment
