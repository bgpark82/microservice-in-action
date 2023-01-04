import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

const Items = () => {
    const ITEM_SERVICE_URL = 'http://localhost:8000/item-service/items';

    const [items, setItems] = useState([])
    const [item, setItem] = useState({
        name: '',
        price: 0,
        amount: 0
    });
    
    const onChangeItem = (e) => {
        const {name, value} = e.target;
        setItem({
            ...item,
            [name]: value
        })
    }

    const onCreateItem = async () => {
        await axios.post(ITEM_SERVICE_URL, item);
        const res = await getItems();
        setItems(res.data)
    }

    const getItems =  () => {
        return axios.get(ITEM_SERVICE_URL);
    }

    useEffect(() => {
        const init = async () => {
            const res = await getItems();
            setItems(res.data)
        }
        init();
    },[])

    return(
        <div>
            <input value={item.name} name="name" placeholder="이름" onChange={onChangeItem}/>
            <input value={item.price} name="price" placeholder="가격" onChange={onChangeItem}/>
            <input value={item.amount} name="amount" placeholder="수량" onChange={onChangeItem}/>
            <button onClick={onCreateItem}>등록</button>
            <div>
                <table>
                    <tbody>
                {items && items.map(item => (
                            <tr key={item.id}>
                                <td>{item.name}</td>
                                <td>{item.price}</td>
                                <td>{item.amount}</td>
                                <td>
                                    <Link to={`/items/${item.id}`}>
                                        <button>옵션 추가</button>
                                    </Link>
                                </td>
                            </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default Items
