import axios from "axios";
export const doSQL =async (params)=>{
    let paramsJson=JSON.stringify(params);
    let url="http://localhost:8080/myBlog/doSQL?params="+encodeURIComponent(paramsJson);
    return await axios.post(url)
}
