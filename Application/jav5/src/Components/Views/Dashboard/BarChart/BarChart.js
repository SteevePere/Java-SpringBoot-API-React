import React, { Component } from 'react';
import Chart from 'chart.js';


class BarChart extends Component
{

	constructor(props)
	{
		super(props);
		this.chartRef = React.createRef();
	}


	componentDidUpdate()
	{
		this.myChart.data.labels = this.props.data.map(d => d.label);
		this.myChart.data.datasets[0].data = this.props.data.map(d => d.value);
		this.myChart.data.datasets[0].backgroundColor = this.props.colors;

		this.myChart.update();
	}


	componentDidMount()
	{
		this.myChart = new Chart(
			this.chartRef.current, {
				type: 'bar',
				options: {
					title: {
						display: this.props.title !== undefined,
						text: this.props.title,
						padding: 20,
					},
					animation: {
						duration: 1500,
					},
					maintainAspectRatio: true,
					legend: {
						display: false,
					},
					scales: {
						yAxes: [
							{
								ticks: {
									min: 0,
									max: this.props.max
								},
								gridLines: {
									display:false
								}
							}
						],
						xAxes: [
							{
								gridLines: {
									display:false
								}
							}
						],
					},
				},
				data: {
					labels: this.props.data && this.props.data.map(d => d.label),
					datasets: [
						{
							data: this.props.data && this.props.data.map(d => d.value),
							backgroundColor: this.props.data && this.props.data.map(d => d.color),
						}
					]
				}
			}
		);
	}


	render()
	{
		return (

			<canvas
				ref={this.chartRef}
			/>
		);
	}
}


export default BarChart;
