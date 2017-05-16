/**
 * ����������Ʒֵĳ���
 *
 * @author SixGangs
 * 2017��5��16��
 */
 
public class BowlingGame 
{
	  public int getBowlingScore(String bowlingCode) throws Exception
	  {
	  	   String[] splitCodes = bowlingCode.split("\\|");//������Ʒ��ַ�����'|'�ָ���������ת���ɷ���
	  	   int spareScore = 0;//��¼spare������µ��״ε÷�
	  	   for(int i =  0; i < splitCodes.length; i++)//�����Ʒ��ַ�������ת������Ӧ�ķ������浽������
	  	   {
	  	   	    String  splitString = splitCodes[i]; 
	  	   	    try//���������ʽ��������
	  	   	    {
	  	   	   		 if(splitString.length() > 2)
	  	   	  		 {
	  	   	    			throw new Exception("�������ݸ�ʽ��������鲢�������롣");
	  	   	       }
	  	   	    }
	  	   	    catch(Exception e)
	  	   	    {
	  	   	    	 e.printStackTrace();
	  	   	    }
	  	   	    for(int j = 0; j < splitString.length(); j++)
	  	   	    {
	  	   	    	char splitChar = splitString.charAt(j);
	  	   	    	if(Character.isDigit(splitChar))//��������֣�ֱ�ӱ���
	  	   				{
	  	   					spareScore = Character.getNumericValue(splitChar); 
	  	   					this.add(spareScore);
	  	   				}
	  	   				else if(splitChar == 'X')//�����'X'��ת����10
	  	   				{
	  	   					this.add(10);
	  	   				}
	  	   				else if(splitChar == '-')//�����'-'��ת����0
	  	   				{
	  	   					this.add(0);
	  	   				}
	  	   				else if(splitChar == '/')//�����'/'��ת����10��ȥ��һ�ε÷�
	  	   				{
	  	   					this.add(10-spareScore);
	  	   				}
	  	   	    	else//�����������
	  	   	    	{
	  	   	    		continue;
	  	   	    	}
	  	   	    }
	  	   }
         return this.scoreForFrame(10);
    }

    /**
     * ��ӱ���û��Ͷ���ĵ÷֣�����Ƿ���
     * ���ҵ�������
     * @param pins int ����
     */
    public void add(int pins)
    {
        addThrow(pins);
        adjustCurrentFrame(pins);
    }
    /**
     * ���һ��Ͷ���󣬵�������,���򽫱��ֵ�һ��Ͷ��(firstThrowInFrame)��Ϊfalse
     * @param pins
     */
    private void adjustCurrentFrame(int pins)
    {
        if (lastBallInFrame(pins))//�������һ��Ͷ������������
            advanceFrame();
        else
            firstThrowInFrame = false;
    }
    /**
     * �жϱ������һ��Ͷ����false�������һ�Σ�true�����һ��
     * @param pins �÷�
     * @return boolean
     */
    private boolean lastBallInFrame(int pins)
    {
        return stricke(pins)||!firstThrowInFrame;
    }
    /**
     * ���ֵ�һ���Ƿ�ȫ��
     * @param pins �÷�
     * @return boolean
     */
    private boolean stricke(int pins)
    {
        return (firstThrowInFrame && pins == 10);
    }
    /**
     * �ı�����
     * ����һ��itsCurrentFrame==1��
     * ��ɵ�һ�ֺ�itsCurrentFrame==2
     * ���������ƣ�ֱ����10�֣�
     */
    private void advanceFrame()
    {
        //Math.min(int,int)����������������С��һ��int
        itsCurrentFrame = Math.min(10, itsCurrentFrame + 1);
    }
    
   /**
     * Ͷ��������Ͷ��������ֵ
     * @param pins Ͷ������
     */
    public void addThrow(int pins)
    {
        itsThrows[itsCurrentThrow++]=pins;
    }
    /**
     * ���㱾�ֵ÷�
     * @param theFrame
     * @return
     */
    public int scoreForFrame(int theFrame)
    {
        ball = 0;
        int score = 0;
        for (int currentFrame = 0; currentFrame < theFrame; currentFrame++)
        {
            if (strike())//��һ��Ͷ��ȫ�У����Ϻ�����Ͷ���ĵ÷�
            {
                score += 10 + nextTwoBallsForStrike();
                ball++;
            }
            else if(spare())//�ڶ���Ͷ�����У����Ϻ�һ��Ͷ���ĵ÷�
            {
                score += 10 + nextBallForSpare();
                ball+=2;
            }
            else//��ͨ�����2��Ͷ�����
            {
                score += twoBallsInFrame();
                ball+=2;
            }
        }
        return score;
    }
    //��һ��ȫ�У����ֵ�һ��ȫ��
    private boolean strike()
    {
        return itsThrows[ball] == 10;
    }
    //�ڶ���Ͷ�����������
    private boolean spare()
    {
        return (itsThrows[ball] + itsThrows[ball + 1]) == 10;
    }
    //���ر��ֺ����εĵ÷�
    private int nextTwoBallsForStrike()
    {
        return itsThrows[ball +1]+itsThrows[ball+2];
    }
    //���ر��ֺ�һ�εĵ÷�
    private int nextBallForSpare()
    {
        return itsThrows[ball +2];
    }
    //���ز�ͬ�ı���2��Ͷ���÷�
    private int twoBallsInFrame()
    {
        return itsThrows[ball]+itsThrows[ball+1];
    }
    private int itsCurrentFrame = 0;//��ǰ����
    private boolean firstThrowInFrame = true;//���ֵ�һ��Ͷ��
    private int ball;//Ͷ������
    private int[] itsThrows = new int[21];//10����Ͷ���Ĵ�������
    private int itsCurrentThrow =  0;
}



    




