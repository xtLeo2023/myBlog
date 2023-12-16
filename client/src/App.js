import React from 'react';
import { Layout, Menu, Avatar, Typography } from 'antd';
import { Routes, Route, useNavigate  } from 'react-router-dom';
import { HomeOutlined, EditOutlined, LogoutOutlined } from '@ant-design/icons';
import { useSelector, useDispatch } from 'react-redux';
import { logoutUser } from './redux/userSlice.js';
import './App.css';
import logo from './logo.svg';
import Home from './blog/home'
import Write from './blog/write'

const { Header, Content } = Layout;
const { Title } = Typography;

function BlogApp() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const user = useSelector(state => state.user.userInfo);

  const menuItems = [
    {
      key: 'home',
      icon: <HomeOutlined />,
      label: '主页',
    },
    {
      key: 'write',
      icon: <EditOutlined />,
      label: '写博客',
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '注销',
    },
  ];

  const handleMenuClick = (e) => {
    if(e.key.trim() === 'logout'){
      dispatch(logoutUser({}));
      navigate('/')
    }else{
      navigate('/blog/'+e.key);
    }
  };


  return (
    <Layout className="layout" style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Header style={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          alignItems: 'center', 
          height: '64px',  
          padding: '0 50px', 
          backgroundColor: 'white', 
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)' ,
          position:'fixed',
          width:'100%',
          top:0,
          zIndex:1
      }}>
        <div style={{ display: 'flex', alignItems: 'center' }}>
          <Avatar src={logo} />
          <Title level={4} style={{ color: 'black', margin: '0 0 0 20px', lineHeight:'64px' }}>{user?user.username:'XXX'}的博客系统</Title>
        </div>
        <Menu theme="light" 
          mode="horizontal" 
          items={menuItems}
          selectable={false} 
          style={{ lineHeight: '64px', border: 'none' }} 
          onClick={handleMenuClick} />
      </Header>
      <Content style={{ padding: '24px', flex: 1, display: 'flex', flexDirection: 'column', marginTop: 64 }}>
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/write" element={<Write />} />
        </Routes>
      </Content>
    </Layout>
  );
}

export default BlogApp;
