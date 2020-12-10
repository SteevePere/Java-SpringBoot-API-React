import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Spin } from 'antd';
import {
	BugOutlined,
	AimOutlined,
} from '@ant-design/icons';

import ExportButton from 'Components/Lib/ExportButton/ExportButton';
import NumberCard from './NumberCard/NumberCard';
import PieChart from './PieChart/PieChart';
import BarChart from './BarChart/BarChart';

import { IssuePriorityModel } from 'Models/IssuePriorityModel';
import { IssueStatusModel } from 'Models/IssueStatusModel';
import { IssueTypeModel } from 'Models/IssueTypeModel';

import { notifications } from './Notifications/notifications';

import { request } from 'Lib/request.js';

import './Dashboard.css';


const Dashboard = () =>
{

	const [metrics, setMetrics] = useState({});
	const [loading, setLoading] = useState(false);


	useEffect(() =>
		{
			getMetrics();

			const interval = setInterval(getMetrics, 60000); // 60s
        	return () => clearInterval(interval); // unmount cleanup
		},
		[]
	);


	const getMetrics = () =>
	{
		setLoading(true);

		request.get(
			'metrics',
			{},
			(metrics) => setMetrics(metrics),
			(e) => console.error(e),
			() => setLoading(false),
		);
	}


	const downloadMetrics = () =>
	{
		request.jsonExport(
			'metrics/export',
			'metrics',
			() => {},
			() => notifications.showDownloadError(),
		);
	}


	const getIssuesPriorityData = () =>
	{
		return Object.entries(IssuePriorityModel).map(([key, value], index) =>
		{
			return {
				label: value.label,
				value: metrics && metrics.numberOfIssuesByPriority &&
					metrics.numberOfIssuesByPriority[key],
				color: value.chartColor,
			};
		});
	}


	const getIssuesStatusData = () =>
	{
		return Object.entries(IssueStatusModel).map(([key, value], index) =>
		{
			return {
				label: value.label,
				value: metrics && metrics.numberOfIssuesByStatus &&
					metrics.numberOfIssuesByStatus[key],
				color: value.chartColor,
			};
		});
	}


	const getIssuesTypeData = () =>
	{
		return Object.entries(IssueTypeModel).map(([key, value], index) =>
		{
			return {
				label: value.label,
				value: metrics && metrics.numberOfIssuesByType &&
					metrics.numberOfIssuesByType[key],
				color: value.chartColor,
			};
		});
	}


	const getIssuesPerBoardData = () =>
	{
		const chartColors = [
			'rgba(77, 201, 246, 0.6)',
			'rgba(246, 112, 25, 0.6)',
			'rgba(83, 123, 196, 0.6)',
			'rgba(245, 55, 148, 0.6)',
			'rgba(172, 194, 54, 0.6)',
			'rgba(22, 106, 143, 0.6)',
			'rgba(0, 169, 80, 0.6)',
			'rgba(88, 89, 91, 0.6)',
			'rgba(133, 73, 186, 0.6)',
		];

		return metrics && metrics.numberOfUsersByBoard &&
			Object.entries(metrics.numberOfUsersByBoard).map(([title, value], index) =>
			{
				return {
					label: title,
					value: value,
					color: chartColors[index % chartColors.length],
				};
			});
	}


	const getUserActivityData = () =>
	{
		return [
			{
				label: 'Active',
				value: metrics && metrics.activeUsers,
				color: 'rgba(22, 106, 143, 0.6)',
			},
			{
				label: 'Inactive',
				value: metrics && metrics.inactiveUsers,
				color: 'rgba(133, 73, 186, 0.6)',
			},
		];
	}


	return (

		loading ? (
			<Spin
				style={{ marginTop: '45vh' }}
			/>
		) :
		(
			<div
				style={{
					padding: 100,
					paddingTop: 0,
					paddingBottom: 0,
					marginTop: '5vh',
					marginBottom: '5vh',
					overflowX: 'hidden',
				}}
			>
				<ExportButton
					download={downloadMetrics}
				/>
				<Row gutter={[32]}>
					<Col
						className='text-center'
						sm={24}
						md={24}
						lg={24}
						xl={12}
					>
						<Row gutter={[32, 32]}>
							<Col
								className='text-center'
								sm={24}
								md={24}
								lg={12}
							>
								<NumberCard
									title='Assigned Issues'
									align='left'
									number={metrics && metrics.assignedIssues}
									delta={8.7}
									icon={<BugOutlined style={{ color: '#2db7f5' }}/>}
									coverImage='kanban_logo_3.jpg'
									backgroundPosition='0 -100px'
								/>
							</Col>
							<Col
								className='text-center'
								sm={24}
								md={24}
								lg={12}
							>
								<NumberCard
									title='Unassigned Issues'
									align='left'
									number={metrics && metrics.unassignedIssues}
									delta={-5.2}
									icon={<BugOutlined style={{ color: '#722ed1' }}/>}
									coverImage='product_review_logo.jpg'
									backgroundPosition='0 -100px'
								/>
							</Col>
						</Row>
						<Row gutter={[32, 32]}>
							<Col
								className='text-center'
								sm={24}
								md={24}
								lg={12}
							>
								<NumberCard
									title='Overdue Milestones'
									align='left'
									number={metrics && metrics.overdueMilestones}
									delta={11.1}
									icon={<AimOutlined style={{ color: 'red' }}/>}
									coverImage='kanban_logo_4.jpg'
									backgroundPosition='0 -100px'
								/>
							</Col>
							<Col
								className='text-center'
								sm={24}
								md={24}
								lg={12}
							>
								<NumberCard
									title='Non-overdue Milestones'
									align='left'
									number={metrics && metrics.notOverdueMilestones}
									delta={-11.1}
									icon={<AimOutlined style={{ color: '#87d068' }}/>}
									coverImage='board_hands_logo.jpg'
									backgroundPosition='0 -130px'
								/>
							</Col>
						</Row>
					</Col>
					<Col
						className='text-center'
						sm={24}
						md={24}
						lg={24}
						xl={12}
					>
						<Card
							className='analyticsSmallCard'
							style={{ height: '93%' }}
						>
							<PieChart
								data={getIssuesTypeData()}
								title='Issues by Type'
								type='doughnut'
							/>
						</Card>
					</Col>
				</Row>

				<Row gutter={[32, 32]}>
					<Col
						className='text-center'
						sm={24}
						md={24}
						lg={24}
						xl={12}
					>
						<Card
							className='analyticsSmallCard'
						>
							<BarChart
								data={getIssuesPriorityData()}
								title='Issues by Priority'
							/>
						</Card>
					</Col>
					<Col
						className='text-center'
						sm={24}
						md={24}
						lg={24}
						xl={12}
					>
						<Card className='analyticsSmallCard'>
							<BarChart
								data={getIssuesStatusData()}
								title='Issues by Status'
							/>
						</Card>
					</Col>
				</Row>

				<Row gutter={[32, 32]}>
					<Col
						className='text-center'
						sm={24}
						md={24}
						lg={24}
						xl={12}
					>
						<Card className='analyticsSmallCard'>
							<PieChart
								data={getIssuesPerBoardData()}
								title='Users per Board'
								type='doughnut'
							/>
						</Card>
					</Col>
					<Col
						className='text-center'
						sm={24}
						md={24}
						lg={24}
						xl={12}
					>
						<Card className='analyticsSmallCard'>
							<PieChart
								data={getUserActivityData()}
								title='Active / Inactive Users'
								type='doughnut'
							/>
						</Card>
					</Col>
				</Row>
			</div>
		)
	);
}


export default Dashboard;
