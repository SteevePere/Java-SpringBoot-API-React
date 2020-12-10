import React from 'react';
import { Row, Col, Divider, Tooltip, Card, Modal, Space, Avatar, Tag } from 'antd';
import {
	EyeOutlined,
	BugOutlined,
} from '@ant-design/icons';
import moment from 'moment';

import VerticalDivider from 'Components/Lib/VerticalDivider/VerticalDivider';

import { IssueTypeModel } from 'Models/IssueTypeModel';
import { IssueStatusModel } from 'Models/IssueStatusModel';
import { IssuePriorityModel } from 'Models/IssuePriorityModel';

const { Meta } = Card;


const ViewIssueView = (props) =>
{

	const {
		issue,
		modalVisible,
		openModal,
		closeModal,
	} = props;


	return (

		<>
			<Tooltip title='View Issue' placement='bottom'>
				<EyeOutlined
					key='view'
					onClick={openModal}
				/>
			</Tooltip>
			<Modal
				title={<Space><BugOutlined/> {issue.title}</Space>}
				visible={modalVisible}
				onOk={closeModal}
				onCancel={closeModal}
				width={800}
			>
				<Card
					cover={
						<div
							style={{
								height: 60,
								backgroundImage: 'url(' + window.location.origin + '/kanban_logo_3.jpg)',
								backgroundSize: 'cover',
								backgroundRepeat: 'no-repeat',
								backgroundPosition: '0 -75px',
								boxShadow: 'inset 0 0 0 200px rgba(255, 255, 255, 0.3)',
							}}
						/>
					}
				>
					<Row>
						<Col
							span={10}
						>
							<Space direction='vertical'>
								<p>
									{IssueTypeModel[issue.type].icon} {IssueTypeModel[issue.type].label}
								</p>
								<p>
									<Tag
										color={IssueStatusModel[issue.status].titleTagColor}
										icon={IssueStatusModel[issue.status].icon}
									>
										{issue.status}
									</Tag>
								</p>
								<p>
									<Tag
										color={IssuePriorityModel[issue.priority].color}
										style={{ float: 'left' }}
									>
										{IssuePriorityModel[issue.priority].label}
									</Tag>
								</p>
							</Space>
						</Col>
						<Col
							span={4}
						>
							<VerticalDivider
								height={90}
							/>
						</Col>
						<Col
							span={10}
						>
							{issue.reporter &&
							<p>
								Reported by: <b>{issue.reporter.username}</b>
								<Meta
									avatar={
										<Avatar
											src={
												issue.reporter ? issue.reporter.isAdmin ?
													window.location.origin + '/admin_logo.png' :
													window.location.origin + '/user_logo.png' :
													window.location.origin + '/user_logo_grey.jpg'
											}
										/>
									}
									style={{ float: 'right' }}
								/>
							</p>}
							<Divider/>
							{issue.assignee ?
							<p>
								Assigned to: <b>{issue.assignee.username}</b>
								<Meta
									avatar={
										<Avatar
											src={
												issue.assignee ? issue.assignee.isAdmin ?
													window.location.origin + '/admin_logo.png' :
													window.location.origin + '/user_logo.png' :
													window.location.origin + '/user_logo_grey.jpg'
											}
										/>
									}
									style={{ float: 'right' }}
								/>
							</p> :
							<p>
								<b>
									Unassigned
								</b>
							</p>}
							<Divider/>
							{issue.milestone &&
							<p>
								Milestone: <b>{issue.milestone.title}</b>
							</p>}
						</Col>
					</Row>
					<Divider/>
					<Row>
						<Col
							span={24}
						>
							{issue.description ?
							<p>{issue.description}</p> :
							<p><i>No Description</i></p>}
						</Col>
					</Row>
					<Divider/>
					<Row>
						<Col
							span={24}
						>
							<li>
								<small>
									<i>
										Created on {moment(issue.creationDate).format('YYYY-MM-DD')}
									</i>
								</small>
							</li>
							<li>
								<small>
									<i>
										Last updated on {moment(issue.updatedDate).format('YYYY-MM-DD')}
									</i>
								</small>
							</li>
						</Col>
					</Row>
				</Card>
			</Modal>
		</>
	);
}


export default ViewIssueView;
