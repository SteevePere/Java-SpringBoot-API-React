import React, { useState, useEffect } from 'react';
import { Tabs, Card } from 'antd';
import {
	BugOutlined,
	AimOutlined,
} from '@ant-design/icons';

import IssueBoard from './IssueBoard';
import MilestoneBoard from './MilestoneBoard';

import { request } from 'Lib/request.js';
import { auth } from 'Lib/auth.js';

const { TabPane } = Tabs;


const KanbanBoard = (props) =>
{

	const [milestones, setMilestones] = useState([]);
	const [allMilestones, setAllMilestones] = useState([]);

	const isAdmin = auth.isAdmin();
	const boardId = props && props.match && props.match.params &&
		props.match.params.id;


	useEffect(() =>
		{
			getMilestones();
		},
		[]  // eslint-disable-line react-hooks/exhaustive-deps
	);


	const getMilestones = () =>
	{
		if (boardId)
		{
			request.get(
				'milestone',
				{
					boardId: boardId,
				},
				(milestones) =>
				{
					setMilestones(milestones);
					setAllMilestones(milestones);
				},
				(e) => console.error(e),
			);
		}
	}


	const filterMilestones = (searchValue) =>
	{
		setMilestones(allMilestones.filter((milestone) =>
			{
				return milestone.title.includes(searchValue) ||
					(milestone.description && milestone.description.includes(searchValue));
			})
		);
	}


	return (

		<Card>
			<Tabs
				defaultActiveKey='1'
				animated={{ inkBar: true, tabPane: true}}
			>
				<TabPane
					tab={
						<span>
							<BugOutlined/>
							Issues
						</span>
					}
					key='1'
				>
					<IssueBoard
						{...props}
						isAdmin={isAdmin}
						boardId={boardId}
						milestones={milestones}
						getMilestones={getMilestones}
					/>
				</TabPane>
				<TabPane
					tab={
						<span>
							<AimOutlined/>
							Milestones
						</span>
					}
					key='2'
				>
					<MilestoneBoard
						{...props}
						isAdmin={isAdmin}
						boardId={boardId}
						milestones={milestones}
						getMilestones={getMilestones}
						filterMilestones={filterMilestones}
					/>
				</TabPane>
			</Tabs>
		</Card>
	);
}


export default KanbanBoard;
