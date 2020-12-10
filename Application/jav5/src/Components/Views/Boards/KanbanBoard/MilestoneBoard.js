import React from 'react';
import { Row, Col, Empty, Input, Space } from 'antd';

import AddMilestone from './Milestone/Add/AddMilestone';
import Milestone from './Milestone/Milestone';

const { Search } = Input;


const MilestoneBoard = (props) =>
{

	const {
		milestones,
		filterMilestones,
	} = props;


	const noMilestones = milestones && milestones.length === 0;


	return (

		<>
			<Row
				style={{
					marginBottom: 40,
				}}
			>
				<Space>
					<AddMilestone
						{...props}
					/>
					<Search
						placeholder='Find Milestones...'
						onSearch={filterMilestones}
						enterButton
						allowClear
					/>
				</Space>
			</Row>
			<Row
				gutter={[36, 36]}
				style={{
					padding: '10px 30px 0 30px',
					minHeight: '90%',
					background: 'rgb(247, 247, 247)',
				}}
			>
				{milestones && milestones.map((milestone) =>
					<Col
						xl={8}
						lg={24}
						md={24}
						xs={24}
						key={'col_' + milestone.id}
					>
						<Milestone
							{...props}
							milestone={milestone}
						/>
					</Col>
				)}
				{noMilestones &&
				<Empty
					description='No Milestones were found'
					style={{
						margin: 'auto',
						marginTop: '25vh',
						marginBottom: '25vh',
					}}
				/>}
			</Row>
		</>
	);
}


export default MilestoneBoard;
