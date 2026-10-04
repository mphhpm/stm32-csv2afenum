````
CSV to enum converter

expected csv format:

first row contains the available alternate functions e.g. AF0..AF15, leave the first cell empty to cater for the connector's name
second and subsequent rows contain at first the name of the connector (e.g. PA0) and available options of each connector 
(e.g. TIM2_CH1,TIM5_CH1)

,AF0,AF1,AF2,AF3,AF4,AF5,AF6,AF7,AF8,AF9,AF10,AF11,AF12,AF13,AF14,AF15
PA0,-,TIM2_CH1,TIM5_CH1,TIM8_ETR,-,-,SPI3_RDY,USART2_CTS,UART4_TX,-,OCTOSPIM_P2_NCS,-,SDMMC2_CMD,AUDIOCLK,TIM2_ETR,EVENTOUT

````

````

create the csv file by copying the AF definitions from the reference manual into a spreadsheet, add some manual fiddling and export it as csv
````
