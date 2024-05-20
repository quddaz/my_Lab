import React,{useEffect, useState} from "react";
import axios from "axios";

function App() {
  const [hello, setHello] = useState('h'); // state 훅

  useEffect(() => {
    axios.get('/api/test')
        .then((res) => {
          setHello(res.data);
        })
  }, []); //이펙트 훅으로 뒤의 변수가 비어있으면 처음 생성 시에 실행
  return (
      <div className="App">
        백엔드에서 온 데이터: {hello}
      </div>
  );
}

export default App;