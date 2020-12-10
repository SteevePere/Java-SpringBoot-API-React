import React, { useState, useEffect } from 'react';
import { Redirect, NavLink } from 'react-router-dom';
import { Row, Card, Form, Input, Divider, Button } from 'antd';
import {
	LoginOutlined,
} from '@ant-design/icons';

import MandatoryField from 'Components/Lib/Form/MandatoryField/MandatoryField';

import { notifications } from '../Notifications/notifications';

import { auth } from 'Lib/auth';

import './Login.css';

const formLayout = {
	labelCol: { span: 8 },
	wrapperCol: { span: 16 },
};


const Login = (props) =>
{

	const [username, setUsername] = useState();
	const [password, setPassword] = useState();
	const [authenticating, setAuthenticating] = useState(false);
	const [isAuthenticated, setIsAuthenticated] = useState(false);

	const homepage = auth.isAdmin() ? '/dashboard' : '/boards';


	useEffect(() =>
		{
			if (authenticating)
			{
				req_authenticate();
			}
		},
		[authenticating] // eslint-disable-line react-hooks/exhaustive-deps
	);


	const req_authenticate = () =>
	{
		auth.req_authenticate(
			username,
			password,
			() =>
			{
				setIsAuthenticated(true);
			},
			(e) =>
			{
				notifications.showAuthenticationError();
			},
			() =>
			{
				setAuthenticating(false);
			},
		);
	}


	const authenticate = () =>
	{
		setAuthenticating(true);
	}


	return !isAuthenticated ? (

		<Row
			type='flex'
			justify='center'
			align='middle'
			style={{ minHeight: '100vh' }}
		>
			<Card
				bordered={false}
				style={{ width: 500 }}
				cover={
					<img
						alt='logo'
						src={window.location.origin + '/kanban_logo_3.jpg'}
					/>
				}
			>
				<Form.Item>
					<h3>Sign In</h3>
					<Divider/>
				</Form.Item>
				<Form
					{...formLayout}
				>
					<Form.Item
						label={<MandatoryField label='Username'/>}
						rules={[{ required: true }]}
					>
						<Input
							value={username}
							onChange={(e) => setUsername(e.target.value)}
						/>
					</Form.Item>
					<Form.Item
						label={<MandatoryField label='Password'/>}
						rules={[{ required: true }]}
					>
						<Input.Password
							value={password}
							onChange={(e) => setPassword(e.target.value)}
						/>
					</Form.Item>
				</Form>
				<Divider/>
				<Form.Item>
					<Button
						block
						type='primary'
						onClick={authenticate}
						loading={authenticating}
					>
						<LoginOutlined/>
						Start
					</Button>
				</Form.Item>
				<NavLink to='/register'>
					Register instead
				</NavLink>
			</Card>
		</Row>
	) :
	(
		<Redirect to={homepage}/>
	);
}


export default Login;
