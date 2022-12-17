import {useEffect} from "react";

function Payment() {



    useEffect(() => {
        const IMP = window.IMP
        IMP.init("imp28387336")

        console.log(IMP)

        IMP.request_pay({
            pg: "kcp",
            pay_method: "card",
            merchant_uid: "ORD20180131-0000011",   //주문번호
            name: "노르웨이 회전 의자",
            amount: 64900,                         // 숫자타입
            buyer_email: "gildong@gmail.com",
            buyer_name: "홍길동",
            buyer_tel: "010-4242-4242",
            buyer_addr: "서울특별시 강남구 신사동",
            buyer_postcode: "01181"
        }, function (rsp) { // callback
            if (rsp.success) {
                console.log("결제 성공")
            } else {
                console.log("결제 실패")
            }
        });
    },[])

    return (
        <div>payment</div>
    )
}

export default Payment
