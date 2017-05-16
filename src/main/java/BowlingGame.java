/**
 * 保龄球比赛计分的程序
 *
 * @author SixGangs
 * 2017年5月16日
 */
 
public class BowlingGame 
{
	  public int getBowlingScore(String bowlingCode) throws Exception
	  {
	  	   String[] splitCodes = bowlingCode.split("\\|");//将输入计分字符串用'|'分隔开，方便转换成分数
	  	   int spareScore = 0;//记录spare球情况下的首次得分
	  	   for(int i =  0; i < splitCodes.length; i++)//解析计分字符串，并转换成相应的分数保存到数组中
	  	   {
	  	   	    String  splitString = splitCodes[i]; 
	  	   	    try//处理输入格式错误的情况
	  	   	    {
	  	   	   		 if(splitString.length() > 2)
	  	   	  		 {
	  	   	    			throw new Exception("输入数据格式有误，请检验并重新输入。");
	  	   	       }
	  	   	    }
	  	   	    catch(Exception e)
	  	   	    {
	  	   	    	 e.printStackTrace();
	  	   	    }
	  	   	    for(int j = 0; j < splitString.length(); j++)
	  	   	    {
	  	   	    	char splitChar = splitString.charAt(j);
	  	   	    	if(Character.isDigit(splitChar))//如果是数字，直接保存
	  	   				{
	  	   					spareScore = Character.getNumericValue(splitChar); 
	  	   					this.add(spareScore);
	  	   				}
	  	   				else if(splitChar == 'X')//如果是'X'，转换成10
	  	   				{
	  	   					this.add(10);
	  	   				}
	  	   				else if(splitChar == '-')//如果是'-'，转换成0
	  	   				{
	  	   					this.add(0);
	  	   				}
	  	   				else if(splitChar == '/')//如果是'/'，转换成10减去第一次得分
	  	   				{
	  	   					this.add(10-spareScore);
	  	   				}
	  	   	    	else//否则继续解析
	  	   	    	{
	  	   	    		continue;
	  	   	    	}
	  	   	    }
	  	   }
         return this.scoreForFrame(10);
    }

    /**
     * 添加本轮没次投掷的得分，存入记分类
     * 并且调整轮数
     * @param pins int 分数
     */
    public void add(int pins)
    {
        addThrow(pins);
        adjustCurrentFrame(pins);
    }
    /**
     * 最后一次投掷后，调整轮数,否则将本轮第一次投掷(firstThrowInFrame)设为false
     * @param pins
     */
    private void adjustCurrentFrame(int pins)
    {
        if (lastBallInFrame(pins))//本轮最后一次投掷，调整轮数
            advanceFrame();
        else
            firstThrowInFrame = false;
    }
    /**
     * 判断本轮最后一次投掷，false不是最后一次，true是最后一次
     * @param pins 得分
     * @return boolean
     */
    private boolean lastBallInFrame(int pins)
    {
        return stricke(pins)||!firstThrowInFrame;
    }
    /**
     * 本轮第一次是否全中
     * @param pins 得分
     * @return boolean
     */
    private boolean stricke(int pins)
    {
        return (firstThrowInFrame && pins == 10);
    }
    /**
     * 改变轮数
     * （第一轮itsCurrentFrame==1，
     * 完成第一轮后，itsCurrentFrame==2
     * ，依次类推，直到第10轮）
     */
    private void advanceFrame()
    {
        //Math.min(int,int)返回两个整形中最小的一个int
        itsCurrentFrame = Math.min(10, itsCurrentFrame + 1);
    }
    
   /**
     * 投掷次数与投掷分数赋值
     * @param pins 投掷分数
     */
    public void addThrow(int pins)
    {
        itsThrows[itsCurrentThrow++]=pins;
    }
    /**
     * 计算本轮得分
     * @param theFrame
     * @return
     */
    public int scoreForFrame(int theFrame)
    {
        ball = 0;
        int score = 0;
        for (int currentFrame = 0; currentFrame < theFrame; currentFrame++)
        {
            if (strike())//第一次投掷全中，加上后两次投掷的得分
            {
                score += 10 + nextTwoBallsForStrike();
                ball++;
            }
            else if(spare())//第二次投掷补中，加上后一次投掷的得分
            {
                score += 10 + nextBallForSpare();
                ball+=2;
            }
            else//普通情况，2次投掷相加
            {
                score += twoBallsInFrame();
                ball+=2;
            }
        }
        return score;
    }
    //第一次全中，本轮第一次全中
    private boolean strike()
    {
        return itsThrows[ball] == 10;
    }
    //第二次投掷，补中情况
    private boolean spare()
    {
        return (itsThrows[ball] + itsThrows[ball + 1]) == 10;
    }
    //返回本轮后两次的得分
    private int nextTwoBallsForStrike()
    {
        return itsThrows[ball +1]+itsThrows[ball+2];
    }
    //返回本轮后一次的得分
    private int nextBallForSpare()
    {
        return itsThrows[ball +2];
    }
    //返回不同的本轮2次投掷得分
    private int twoBallsInFrame()
    {
        return itsThrows[ball]+itsThrows[ball+1];
    }
    private int itsCurrentFrame = 0;//当前轮数
    private boolean firstThrowInFrame = true;//本轮第一次投掷
    private int ball;//投掷次数
    private int[] itsThrows = new int[21];//10轮所投掷的次数数组
    private int itsCurrentThrow =  0;
}



    




