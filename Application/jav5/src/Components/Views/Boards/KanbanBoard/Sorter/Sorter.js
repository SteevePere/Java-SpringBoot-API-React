import React from 'react';
import { Space, Switch } from 'antd';


const VerticalDivider = () =>
{
	return (
		<hr
			style={{
				height: 15,
				display: 'inline-block',
				border: '1px solid #f0f0f0'
			}}
		/>
	);
}


const Sorter = (props) =>
{

	const {
		sortByPriority,
		sortByType,
		sortByAssignment,
		setSortByPriority,
		setSortByType,
		setSortByAssignment,
	} = props;


	return (

		<Space size={10} style={{ marginLeft: 20, lineHeight: '0em' }}>
			<Switch
				size='small'
				checked={sortByPriority}
				onChange={() => setSortByPriority(!sortByPriority)}
			/>
			<small>High Priority First</small>
			<VerticalDivider/>
			<Switch
				size='small'
				checked={sortByType}
				onChange={() => setSortByType(!sortByType)}
			/>
			<small>Bugs First</small>
			<VerticalDivider/>
			<Switch
				size='small'
				checked={sortByAssignment}
				onChange={() => setSortByAssignment(!sortByAssignment)}
			/>
			<small>Unassigned First</small>
		</Space>
	);
}


export default Sorter;
