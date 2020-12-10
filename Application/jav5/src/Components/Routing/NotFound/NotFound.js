import React from 'react';
import { NavLink } from 'react-router-dom';
import { Card, Row, Col, Button } from 'antd';
import {
	HomeOutlined,
} from '@ant-design/icons';

import './NotFound.css';


const NotFound = (props) =>
{
	return (

		<Card
			className='not-found'
			bordered={false}
		>
			<Row
				gutter={[24, 24]}
			>
				<Col
					span={24}
					style={{ textAlign: 'center' }}
				>
					<img
						src={window.location.origin + '/404.png'}
						alt='Not Found'
						style={{ margin: 'auto', height: 330 }}
					/>
				</Col>
				<Col
					span={24}
					style={{ textAlign: 'center' }}
				>
					<NavLink to='/users'>
						<Button>
							<HomeOutlined/>
							Help me, Obi-Wan Kenobi !
						</Button>
					</NavLink>
				</Col>
			</Row>
		</Card>
	);
}


export default NotFound;
