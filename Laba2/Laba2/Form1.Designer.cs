namespace Laba2
{
    partial class Form1
    {
        /// <summary>
        /// Требуется переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Обязательный метод для поддержки конструктора - не изменяйте
        /// содержимое данного метода при помощи редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.pointsCountBox = new System.Windows.Forms.TextBox();
            this.acceptButton = new System.Windows.Forms.Button();
            this.findClassesButton = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.Color.White;
            this.pictureBox1.Location = new System.Drawing.Point(12, 39);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(716, 347);
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            // 
            // pointsCountBox
            // 
            this.pointsCountBox.Location = new System.Drawing.Point(12, 12);
            this.pointsCountBox.Name = "pointsCountBox";
            this.pointsCountBox.Size = new System.Drawing.Size(101, 20);
            this.pointsCountBox.TabIndex = 1;
            this.pointsCountBox.Text = "1000";
            // 
            // acceptButton
            // 
            this.acceptButton.Location = new System.Drawing.Point(119, 10);
            this.acceptButton.Name = "acceptButton";
            this.acceptButton.Size = new System.Drawing.Size(75, 23);
            this.acceptButton.TabIndex = 2;
            this.acceptButton.Text = "Accept";
            this.acceptButton.UseVisualStyleBackColor = true;
            this.acceptButton.Click += new System.EventHandler(this.acceptButton_Click);
            // 
            // findClassesButton
            // 
            this.findClassesButton.Location = new System.Drawing.Point(211, 10);
            this.findClassesButton.Name = "findClassesButton";
            this.findClassesButton.Size = new System.Drawing.Size(75, 23);
            this.findClassesButton.TabIndex = 3;
            this.findClassesButton.Text = "Find Classes";
            this.findClassesButton.UseVisualStyleBackColor = true;
            this.findClassesButton.Click += new System.EventHandler(this.findClassesButton_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(304, 10);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 4;
            this.button1.Text = "Clear";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(740, 398);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.findClassesButton);
            this.Controls.Add(this.acceptButton);
            this.Controls.Add(this.pointsCountBox);
            this.Controls.Add(this.pictureBox1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Laba 2";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.TextBox pointsCountBox;
        private System.Windows.Forms.Button acceptButton;
        private System.Windows.Forms.Button findClassesButton;
        private System.Windows.Forms.Button button1;
    }
}

