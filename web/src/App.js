import './App.css';
import Login from "./component/Login";
import Naver from "./component/Naver";
import Kakao from "./component/Kakao";
import {Route, Routes} from "react-router-dom";
import Payment from "./component/Payment";
import Items from "./component/Items";
import Item from "./component/item";
import PaymentSuccess from "./component/PaymentSuccess";
import PaymentCancel from "./component/PaymentCancel";



function App() {

  return (
      <Routes>
        <Route path="/" element={<Login/>} exact/>
        <Route path="/signin/naver" element={<Naver/>} exact/>
        <Route path="/signin/kakao" element={<Kakao/>} exact/>
        <Route path="/payment" element={<Payment/>} exact/>
        <Route path="/payment/success" element={<PaymentSuccess/>} exact/>
        <Route path="/payment/cancel" element={<PaymentCancel/>} exact/>
        <Route path="/items" element={<Items/>} exact/>
        <Route path="/items/:id" element={<Item/>}/>
      </Routes>
  )


}

export default App;
