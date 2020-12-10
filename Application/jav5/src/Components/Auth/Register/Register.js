import React, { useState, useEffect } from 'react';
import { Redirect, NavLink } from 'react-router-dom';
import { Row, Col, Card, Form, Input, Divider, Button, Select } from 'antd';
import {
	UserAddOutlined,
	UndoOutlined,
} from '@ant-design/icons';

import MandatoryField from 'Components/Lib/Form/MandatoryField/MandatoryField';

import { notifications } from '../Notifications/notifications';

import { request } from 'Lib/request';

import { RolesModel } from 'Models/RolesModel';

const { Option } = Select;

const formLayout = {
	labelCol: { span: 8 },
	wrapperCol: { span: 16 },
};


const Register = (props) =>
{

	const [username, setUsername] = useState();
	const [password, setPassword] = useState();
	const [confirmPassword, setConfirmPassword] = useState();
	const [role, setRole] = useState();
	const [isRegistred, setIsRegistred] = useState(false);
	const [registering, setRegistering] = useState(false);


	useEffect(() =>
	{
		if (registering)
		{
			req_register();
		}
	},
	[registering] // eslint-disable-line react-hooks/exhaustive-deps
	);


	const req_register = () =>
	{
		if (formIsValid())
		{
			request.post(
				'register',
				{
					username: username,
					password: password,
					role: role,
				},
				() =>
				{
					setIsRegistred(true);
					notifications.showRegistrationSuccess();
				},
				(error) =>
				{
					if (error.status === 409)
					{
						notifications.showRegistrationError(
							'This Username is already taken.'
						);
					}

					else notifications.showRegistrationError();
				},
				() => setRegistering(false),
			);
		}

		else
		{
			notifications.showInvalidFormError();
			setRegistering(false);
		}
	}


	const register = () => setRegistering(true);


	const handleRoleChange = (value) => setRole(value);


	const formIsValid = () =>
	{
		return role &&
			username &&
			password &&
			confirmPassword === password;
	}


	return !isRegistred ? (

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
					<h3>Register</h3>
					<Divider/>
				</Form.Item>
				<Form
					{...formLayout}
				>
					<Form.Item
							label={<MandatoryField label='Role'/>}
							rules={[{ required: true }]}
					>
						<Select
							showSearch
							placeholder='Select a Role'
							style={{ width: '100%' }}
							value={role}
							onChange={handleRoleChange}
						>
						{Object.keys(RolesModel).map((key) =>
							<Option
								key={RolesModel[key]}
								value={RolesModel[key]}
							>
								{key}
							</Option>
						)}
						</Select>
					</Form.Item>
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
					<Form.Item
						label={<MandatoryField label='Confirm Password'/>}
						hasFeedback
					>
						<Input.Password
							value={confirmPassword}
							onChange={(e) => setConfirmPassword(e.target.value)}
							autoComplete='off'
						/>
					</Form.Item>
				</Form>
				<Divider/>
				<Row gutter={[12, 12]}>
					<Col span={12}>
						<Form.Item>
							<NavLink to='/login'>
								<Button
									block
									type='danger'
								>
									<UndoOutlined/>
									Back to Login
								</Button>
							</NavLink>
						</Form.Item>
					</Col>
					<Col span={12}>
						<Form.Item>
							<Button
								block
								type='primary'
								onClick={register}
								loading={registering}
							>
								<UserAddOutlined/>
								Register
							</Button>
						</Form.Item>
					</Col>

				</Row>
			</Card>
		</Row>
	) :
	(
		<Redirect to='/login'/>
	);
}


export default Register;
