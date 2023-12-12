import axios from "axios";
export const doUser =async (params)=>{
    let paramsJson=JSON.stringify(params);
    let url="http://localhost:8080/myBlog/doUser?params="+encodeURIComponent(paramsJson);
    return await axios.post(url)
}
