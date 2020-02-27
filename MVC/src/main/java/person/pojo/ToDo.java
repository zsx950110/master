
package person.pojo;
import java.util.Date;


/**
 * @description 待办页面字段对应的vo类
 * @author Zhang Shaoxuan
 * @version 1.0, 2019-2-25
 * @since 1.0, 2019-2-25
 */
public class ToDo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String assignee;
    private String subject;
    // 套账号
    private String setCode;
    private String productId;
    private String amount;
    // 发起人
    private String frontOperator;
    private Date acceptTime;
    private String taskId;
    private String taskType;

    private int executorFlag;
    //存储跑批节点页面的的url
    private String ppUrl;
    /**
     * @return the ppUrl
     */
    public String getPpUrl(){
        return this.ppUrl;
    }

    /**
     * @param ppUrl the ppUrl to set
     */
    public void setPpUrl(String ppUrl){
        this.ppUrl = ppUrl;
    }

    private String formUrl;
    private String taskName;
    private String processInstanceId;

    private Date createTime;
    // 往账付款方账号/来账收款方账号
    private String account;
    //判断是否是紧急任务，1为是，0为否
    private String emergency;
    /**
     * 任务链业务类类型
     */
    private String chainTypeName;

    /**
     * @return the chainTypeName
     */
    public String getChainTypeName(){
        return this.chainTypeName;
    }

    /**
     * @param chainTypeName the chainTypeName to set
     */
    public void setChainTypeName(String chainTypeName){
        this.chainTypeName = chainTypeName;
    }

    /**
     * @return the account
     */
    public String getAccount(){
        return this.account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account){
        this.account = account;
    }

    /**
     * @return the emergency
     */
    public String getEmergency(){
        return this.emergency;
    }

    /**
     * @param emergency the emergency to set
     */
    public void setEmergency(String emergency){
        this.emergency = emergency;
    }

    /**
     * @return the id
     */
    public String getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * @return the assignee
     */
    public String getAssignee() {

        return this.assignee;
    }

    /**
     * @param assignee
     *            the assignee to set
     */
    public void setAssignee(String assignee) {

        this.assignee = assignee;
    }

    /**
     * @return the subject
     */
    public String getSubject() {

        return this.subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {

        this.subject = subject;
    }

    /**
     * @return the setCode
     */
    public String getSetCode() {

        return this.setCode;
    }

    /**
     * @param setCode
     *            the setCode to set
     */
    public void setSetCode(String setCode) {

        this.setCode = setCode;
    }

    /**
     * @return the productId
     */
    public String getProductId() {

        return this.productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId) {

        this.productId = productId;
    }

    /**
     * @return the amount
     */
    public String getAmount() {

        return this.amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(String amount) {

        this.amount = amount;
    }

    /**
     * @return the frontOperator
     */
    public String getFrontOperator() {

        return this.frontOperator;
    }

    /**
     * @param frontOperator
     *            the frontOperator to set
     */
    public void setFrontOperator(String frontOperator) {

        this.frontOperator = frontOperator;
    }

    /**
     * @return the acceptTime
     */
    public Date getAcceptTime() {

        return this.acceptTime;
    }

    /**
     * @param acceptTime
     *            the acceptTime to set
     */
    public void setAcceptTime(Date acceptTime) {

        this.acceptTime = acceptTime;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {

        return this.taskId;
    }

    /**
     * @param taskId
     *            the taskId to set
     */
    public void setTaskId(String taskId) {

        this.taskId = taskId;
    }

    /**
     * @return the taskType
     */
    public String getTaskType() {

        return this.taskType;
    }

    /**
     * @param taskType
     *            the taskType to set
     */
    public void setTaskType(String taskType) {

        this.taskType = taskType;
    }

    /**
     * @return the executorFlag
     */
    public int getExecutorFlag(){
        return this.executorFlag;
    }

    /**
     * @param executorFlag the executorFlag to set
     */
    public void setExecutorFlag(int executorFlag){
        this.executorFlag = executorFlag;
    }

    /**
     * @return the formUrl
     */
    public String getFormUrl() {

        return this.formUrl;
    }

    /**
     * @param formUrl
     *            the formUrl to set
     */
    public void setFormUrl(String formUrl) {

        this.formUrl = formUrl;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {

        return this.taskName;
    }

    /**
     * @param taskName
     *            the taskName to set
     */
    public void setTaskName(String taskName) {

        this.taskName = taskName;
    }

    /**
     * @return the processInstanceId
     */
    public String getProcessInstanceId() {

        return this.processInstanceId;
    }

    /**
     * @param processInstanceId
     *            the processInstanceId to set
     */
    public void setProcessInstanceId(String processInstanceId) {

        this.processInstanceId = processInstanceId;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {

        return this.createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

}
