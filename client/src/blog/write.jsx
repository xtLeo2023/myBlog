import React, { useState } from 'react';
import { Button, Typography, Input } from 'antd';
import { SendOutlined } from '@ant-design/icons';
import { useSelector } from 'react-redux';
import { doSQL } from '../api/doSQL';
import { Routes, Route, useNavigate  } from 'react-router-dom';

const { Title } = Typography;
const { TextArea } = Input;

function getCurrentFormattedDate() {
  const currentDate = new Date();

  // 获取年、月、日、时、分、秒
  const year = currentDate.getFullYear();
  const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
  const day = currentDate.getDate().toString().padStart(2, '0');
  const hours = currentDate.getHours().toString().padStart(2, '0');
  const minutes = currentDate.getMinutes().toString().padStart(2, '0');
  const seconds = currentDate.getSeconds().toString().padStart(2, '0');

  // 格式化为 'YYYY-MM-DD HH:mm:ss'
  const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

  return formattedDate;
}

const Write = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const user = useSelector(state => state.user.userInfo);
  const navigate = useNavigate();

  const handlePublish = async () => {
    // 这里可以添加发布博客的逻辑
    console.log('发布博客', title, content);
    let p={};
    p.procedure='b001';
    p.title=title;
    p.content=content;
    p.userid=user.username;
    p.posttime=getCurrentFormattedDate();
    let rs=await doSQL(p);

    if(rs.data[0].result.trim() === 'yes'){
      navigate('/blog/home');
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
      <div style={{ textAlign:'left', width: 800 }}> 
        <Title level={3} style={{ marginBottom: 24 }}>写博客</Title>
      </div>
      <Input 
        placeholder="博客标题" 
        value={title} 
        onChange={(e) => setTitle(e.target.value)} 
        style={{ marginBottom: 16, width: 800 }} 
      />
      <TextArea 
        rows={20} 
        placeholder="博客内容" 
        value={content} 
        onChange={(e) => setContent(e.target.value)} 
        style={{ marginBottom: 16, width: 800 }} 
      />
      <div style={{ textAlign:'right', width: 800 }}>
        <Button type="primary" icon={<SendOutlined />} onClick={handlePublish}>发布博客</Button>
      </div>
    </div>
  );
};

export default Write;
