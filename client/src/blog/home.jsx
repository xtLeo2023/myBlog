import React, { useEffect, useState } from 'react';
import { Card, Row, Col, Button, Typography } from 'antd';
import { useSelector } from 'react-redux';
import a001 from '../images/001.png';
import { doSQL } from '../api/doSQL';

const { Title } = Typography;
const loadItems = [
  {
    blogid: 1,
    title: "",
    content: "",
    postTime: ""
  }
];

const Home = () => {
  const [items, setItems] = useState(loadItems);
  const [selectedItem, setSelectedItem] = useState(null); // 新增状态，用于存储选中的博客详情

  const user = useSelector(state => state.user.userInfo);

  useEffect(()=>{
    loaddata();
  },[])

  const loaddata=async ()=>{
    let p={};
    p.sql="select * from blog where userid='"+user.username+"' order by posttime desc";
    let rs=await doSQL(p);
    setItems(rs.data);
  }

  const handleViewDetails = (item) => {
    setSelectedItem(item); // 更新选中的博客详情
  };

  const handleCloseDetails = () => {
    setSelectedItem(null); // 清除博客详情，重新显示博客列表
  };

  const handleDeleteBlog = async ()=> {
    let p={};
    p.update='true';
    p.sql="delete from blog where blogid="+selectedItem.blogId;
    console.log(selectedItem,p)
    let rs=await doSQL(p);
    console.log(222,rs)
    if(rs.data.trim() === 'success'){
      loaddata();
      setSelectedItem(null)
    }
  }

  return (
    <div style={{ display: 'flex', justifyContent: 'center' }}>
      <div style={{ width: 1200 }}>
        <Title level={3} style={{ marginBottom: 24 }}>首页</Title>
        <Row gutter={16}>
          <Col span={3}>
            <Card
              style={{ width: 120, height: 200 }}
              cover={<img alt="example" src={a001} />}
            >
              <Card.Meta title={user.username} description={`博客数量: ${items.length}`} />
            </Card>
          </Col>
          <Col span={20} style={{overflow:'auto'}}>
            {selectedItem ? (
              <div>
                <Card style={{marginBottom:16,textAlign:'center',height:550}}>
                  <h3>{selectedItem.title}</h3>
                  <p>{selectedItem.postTime}</p>
                  <p style={{ textAlign: 'left' }}>{selectedItem.content}</p>
                </Card>
                <div style={{textAlign:'right'}}>
                  <Button type="primary" danger onClick={handleDeleteBlog} style={{marginRight:16}}>删除博客</Button>
                  <Button type="primary" onClick={handleCloseDetails}>返回主页</Button>
                </div>
              </div>
            ) : (
              items.map(item => (
                <Card key={item.blogid} style={{ marginBottom: 16 }}>
                  <Row justify="space-between" align="middle">
                    <Col span={20}>
                      <h3>{item.title}</h3>
                      <p style={{
                        display: '-webkit-box',
                        WebkitBoxOrient: 'vertical',
                        WebkitLineClamp: 2,
                        overflow: 'hidden',
                        textOverflow: 'ellipsis'
                      }}>
                        {item.content}
                      </p>
                      <p>{item.postTime}</p>
                    </Col>
                    <Col span={3}>
                      <Button type="primary" onClick={() => handleViewDetails(item)}>查看详情</Button>
                    </Col>
                  </Row>
                </Card>
              ))
            )}
          </Col>
        </Row>
      </div>
    </div>
  );
};

export default Home;