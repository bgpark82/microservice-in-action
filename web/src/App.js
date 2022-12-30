import './App.css';
import Login from "./component/Login";
import Naver from "./component/Naver";
import Kakao from "./component/Kakao";
import {Route, Routes} from "react-router-dom";
import Payment from "./component/Payment";
import Items from "./component/Items";



function App() {

  return (
      <Routes>
        <Route path="/" element={<Login/>} exact/>
        <Route path="/signin/naver" element={<Naver/>} exact/>
        <Route path="/signin/kakao" element={<Kakao/>} exact/>
        <Route path="/payment" element={<Payment/>} exact/>
        <Route path="/item" element={<Items/>} exact/>
      </Routes>
  )


}

export default App;
